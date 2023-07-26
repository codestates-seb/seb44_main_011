package com.seb44main011.petplaylist.domain.playlist.service;

import com.seb44main011.petplaylist.domain.member.entity.Member;
import com.seb44main011.petplaylist.domain.member.service.MemberService;
import com.seb44main011.petplaylist.domain.music.dto.MusicDto;
import com.seb44main011.petplaylist.domain.music.entity.Music;
import com.seb44main011.petplaylist.domain.music.mapper.MusicMapper;
import com.seb44main011.petplaylist.domain.music.service.mainService.MusicService;
import com.seb44main011.petplaylist.domain.playlist.entity.entityTable.PlayList;
import com.seb44main011.petplaylist.domain.playlist.mapper.MusicListMapper;
import com.seb44main011.petplaylist.domain.playlist.repository.MusicListRepository;
import com.seb44main011.petplaylist.global.error.BusinessLogicException;
import com.seb44main011.petplaylist.global.error.ExceptionCode;
import com.seb44main011.petplaylist.global.utils.PageNationCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MusicListService {
    //TODO: 노래가 삭제되면 개인의 플레이 리스트에 있는 곡도 안보이게 할건지 보이게는 하되 상세 조회시 에러를 출력하게 할것인지 논의
    private final MusicListRepository repository;
    private final MusicListMapper mapper;
    private final MusicService musicService;
    private final MemberService memberService;


    public void updatePlayList(MusicDto.PostRequest postRequest, String authMemberEmail, long memberId) {
        Member auhtMember = isCheckingToken(authMemberEmail, memberId);
        PlayList playList = mapper.memberAndMusicToMusicList(auhtMember,musicService.findMusic(postRequest.getMusicId()));
        addMusicList(playList);
    }

    public Page<PlayList> findPersonalMusicListsPage(String authMemberEmail,long memberId, int page){
        isCheckingToken(authMemberEmail,memberId);
        List<PlayList> playListList = findPersonalMusicLists(memberId);
        Pageable pageable = PageNationCreator.getPageOfDesc(page,PageNationCreator.ORIGIN_PAGE_SIZE_OF_SIX);
        return PageNationCreator.createPage(playListList,pageable);
    }

    public List<PlayList> findPersonalMusicLists(long memberId){
        return repository.findAllByMember_MemberIdAndMusicStatus(memberId, Music.Status.ACTIVE);
    }
    public List<PlayList> findPersonalMusicLists(String email){
        return repository.findAllByMember_EmailAndMusicStatus(email,Music.Status.ACTIVE);
    }
    public void addMusicList(PlayList playList){
        verifyExistsMusicList(playList);
        repository.save(playList);
    }


//    public PlayList createMusicList(PersonalPlayList playList, Music music){
//        return mapper.memberAndMusicToMusicList(playList,music);
//    }

    public void deletePlayList(Member member, Music music){
        PlayList getPlayList = getMusicList(music.getMusicId(),member.getMemberId());
        repository.delete(getPlayList);
    }

    private void verifyExistsMusicList(PlayList playList) {
        Optional<PlayList> optionalMusicList = repository.findByMusic_MusicIdAndMember_MemberId(
                playList.getMusic().getMusicId(), playList.getMember().getMemberId());
        if (optionalMusicList.isPresent()){
            throw new BusinessLogicException(ExceptionCode.LIKED_MUSIC_EXISTS);
        }
    }

    private PlayList getMusicList(long musicId, long memberId) {
        return repository.findByMusic_MusicIdAndMember_MemberId(musicId, memberId)
                .orElseThrow(
                        () -> new BusinessLogicException(ExceptionCode.MUSIC_NOT_FOUND)
                );
    }


    private Member isCheckingToken(String authMemberEmail, long memberId) {
        Member authMember = memberService.findByMemberFromEmail(authMemberEmail);
        if (authMember.getMemberId()!= memberId){
            throw new BusinessLogicException(ExceptionCode.MEMBER_MISMATCH);
        }
        return authMember;
    }



}
