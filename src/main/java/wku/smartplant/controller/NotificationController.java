package wku.smartplant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wku.smartplant.domain.Member;
import wku.smartplant.dto.ResponseDTO;
import wku.smartplant.dto.member.MemberJoinRequest;
import wku.smartplant.dto.notification.NotificationDTO;
import wku.smartplant.jwt.SecurityUtil;
import wku.smartplant.service.NotificationService;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static wku.smartplant.dto.ResponseEntityBuilder.build;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    @Operation(summary = "유저의 알림 리스트 불러오기",
            description = "현재 로그인한 유저의 알림 리스트 불러오기 (토큰 필요)" ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "알림 리스트 반환 성공"),
            })
    public ResponseEntity<ResponseDTO<List<NotificationDTO>>> getList() {

        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        List<NotificationDTO> notifications = notificationService.getNotificationListByMemberId(currentMemberId);

        return build("알림 목록 입니다.", OK, notifications);
    }
    @GetMapping("/count")
    @Operation(summary = "유저의 읽지 않은 알림 수 얻기",
            description = "현재 로그인한 유저의 읽지 않은 알림 갯수 얻기 (토큰 필요)" ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "알림 리스트 반환 성공"),
            })
    public ResponseEntity<ResponseDTO<Long>> getNotReadCount() {

        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        long count = notificationService.getNotReadCountByMemberId(currentMemberId);

        return build("읽지 않은 알림 갯수 입니다.", OK, count);
    }
    @PatchMapping("/{notificationId}")
    @Operation(summary = "유저의 알림 읽음 처리",
            description = "PathVariable의 알림을 읽음 처리 해줌 (토큰 필요)" ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "알림 반환 성공. content에는 읽음 처리된 알림 id가 있음"),
            })
    public ResponseEntity<ResponseDTO<Long>> readNotification(@PathVariable("notificationId") Long notificationId) {

        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        Long notification = notificationService.readNotification(notificationId, currentMemberId);
        return build("읽음 처리가 완료되었습니다.", OK, notification);
    }
    @DeleteMapping("/{notificationId}")
    @Operation(summary = "유저의 알림 하나 삭제",
            description = "PathVariable의 알림을 삭제 처리 해줌 (토큰 필요)" ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "알림 삭제 성공. content에는 삭제된 알림 id가 있음"),
            })
    public ResponseEntity<ResponseDTO<Long>> deleteOneNotification(@PathVariable("notificationId") Long notificationId) {

        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        Long notification = notificationService.deleteOneNotification(notificationId, currentMemberId);
        return build("삭제 처리가 완료되었습니다.", OK, notification);
    }

    @DeleteMapping
    @Operation(summary = "유저의 알림 전부 삭제",
            description = "해당 유저의 알림을 모두 삭제 처리 해줌 (토큰 필요)" ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "알림 삭제 성공 별도의 content는 없음"),
            })
    public ResponseEntity<ResponseDTO<?>> deleteAllNotification() {

        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        notificationService.deleteAllNotification(currentMemberId);
        return build("모든 알림의 삭제 처리가 완료되었습니다.", OK);
    }

}
