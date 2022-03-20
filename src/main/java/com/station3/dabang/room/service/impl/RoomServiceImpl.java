package com.station3.dabang.room.service.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.station3.dabang.common.exception.BizRuntimeException;
import com.station3.dabang.common.exception.ErrorCode;
import com.station3.dabang.common.util.ObjectUtil;
import com.station3.dabang.member.domain.Email;
import com.station3.dabang.member.domain.Member;
import com.station3.dabang.member.domain.MemberRepository;
import com.station3.dabang.member.domain.QMember;
import com.station3.dabang.room.controller.dto.request.RoomCreateRequest;
import com.station3.dabang.room.controller.dto.request.RoomSearchRequest;
import com.station3.dabang.room.controller.dto.request.RoomUpdateRequest;
import com.station3.dabang.room.controller.dto.response.RoomCreateResponse;
import com.station3.dabang.room.controller.dto.response.RoomDetailResponse;
import com.station3.dabang.room.controller.dto.response.RoomListResponse;
import com.station3.dabang.room.domain.Deal;
import com.station3.dabang.room.domain.DealRepository;
import com.station3.dabang.room.domain.DealType;
import com.station3.dabang.room.domain.QDeal;
import com.station3.dabang.room.domain.QRoom;
import com.station3.dabang.room.domain.Room;
import com.station3.dabang.room.domain.RoomRepository;
import com.station3.dabang.room.domain.RoomType;
import com.station3.dabang.room.service.RoomService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {

	private final RoomRepository roomRepository;

	private final MemberRepository memberRepository;

	private final DealRepository dealRepository;
	
	private final EntityManager em;

	@Override
	@Transactional
	public RoomCreateResponse registerRoom(RoomCreateRequest roomCreateRequest) {
		Room room = roomCreateRequest.toRoom();
		Member member = memberRepository.findByEmail(new Email(getUserEmail()));

		//roomCreateRequest.toDeals().forEach(obj -> room.addDeal(obj));
		//람다식으로 표현하면 room = roomRepository.save(room); 해당 함수를 쓸수가 없어서 테스트코드를 만들수가 없음.. 더좋은 방법을 생각해보자. 
		for(Deal deal:roomCreateRequest.toDeals()) {
			room.addDeal(deal);
		}

		room.setMember(member);
		room = roomRepository.save(room);
		dealRepository.saveAll(room.getDeals());
		return RoomCreateResponse.from(room);
	}

	public String getUserEmail() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		return user.getUsername();
	}
	
	/**
	 * 정책에 따라서 체크 안해도 될듯..!?
	 * @param memberId
	 */
	public void memberValidation(Long memberId) {
		Optional<Member> member = memberRepository.findById(memberId);
		if(member.isEmpty() || !member.get().getEmail().getValue().equals(getUserEmail())) {
    	  	throw new BizRuntimeException(ErrorCode.LOGIN_INFO_INVALID);
		}
	}

	@Override
	public RoomListResponse getMemberRoomList(Long memberId) {
		memberValidation(memberId);
		return RoomListResponse.from(roomRepository.findByMemberId(memberId));
	}

	@Override
	public RoomDetailResponse getRoomDetail(Long roomId) {
		Optional<Room> room = roomRepository.findById(roomId);
		if(!room.isPresent()) throw new BizRuntimeException(ErrorCode.ROOM_NOT_FOUND);
		return RoomDetailResponse.from(room.get());
	}

	@Override
	public HttpStatus deleteRoom(Long roomId) {
		getRoomByIdAndEmail(roomId);
		roomRepository.deleteById(roomId);
		return HttpStatus.OK;
	}

	@Override
	@Transactional
	public HttpStatus updateRoom(RoomUpdateRequest roomUpdateRequest) {
		Room room = getRoomByIdAndEmail(roomUpdateRequest.getRoomId());
		room.setType(roomUpdateRequest.getRoomType());
		
		roomRepository.save(room);
		dealRepository.deleteAll(room.getDeals());
		room.getDeals().clear();
		
		roomUpdateRequest.toDeals().forEach(obj -> {
			room.addDeal(obj);
		});
		
		dealRepository.saveAll(room.getDeals());
		return HttpStatus.OK;
	}
	
	public Room getRoomByIdAndEmail(long roomId) {
		Room room = roomRepository.findByRoomIdAndEmail(roomId, new Email(getUserEmail()));
		if(room == null) throw new BizRuntimeException(ErrorCode.NOT_AUTH_DELETE_ROOM);
		return room;
	}

	@Override
	public RoomListResponse getRoomList(RoomSearchRequest roomSearchRequest) {
		JPAQueryFactory query = new JPAQueryFactory(em);
		
		QRoom room = QRoom.room;
		QDeal deal = QDeal.deal;
		QMember member = QMember.member;
		
		//이놈을 어이 해야할고... QueryDsl 공부가 필요
		BooleanBuilder builder = new BooleanBuilder();
		if(!ObjectUtil.isEmpty(roomSearchRequest.getRoomType())) {
			builder.and(room.type.eq(RoomType.valueOf(roomSearchRequest.getRoomType())));
		}
		if(!ObjectUtil.isEmpty(roomSearchRequest.getDealType())) {
			builder.and(deal.type.eq(DealType.valueOf(roomSearchRequest.getDealType())));	
		}
		if(!ObjectUtil.isEmpty(roomSearchRequest.getDepositStart())) {
			builder.and(deal.deposit.goe(roomSearchRequest.getDepositStart()));
		}
		if(!ObjectUtil.isEmpty(roomSearchRequest.getDepositEnd())) {
			builder.and(deal.deposit.loe(roomSearchRequest.getDepositEnd()));
		}
		if(!ObjectUtil.isEmpty(roomSearchRequest.getPriceStart())) {
			builder.and(deal.price.goe(roomSearchRequest.getPriceStart()));
		}
		if(!ObjectUtil.isEmpty(roomSearchRequest.getPriceEnd())) {
			builder.and(deal.price.loe(roomSearchRequest.getPriceEnd()));
		}
		
		List<Room> memberList = query
				.selectFrom(room)
				.join(room.deals, deal)
				.join(room.member, member).fetchJoin()
				.where(builder)
				.groupBy(room.id, room.type, member.email)
				.orderBy(room.id.desc())
				.offset(roomSearchRequest.getOffset())
				.limit(roomSearchRequest.getLimit())
				.fetch();
		
		return 	RoomListResponse.from(memberList);
	}

}
