package com.example.convo.contoller;

import com.example.convo.model.Channel;
import com.example.convo.model.User;
import com.example.convo.service.ChannelService;
import com.example.convo.service.UserService;
import com.example.convo.http.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    @Autowired
    private UserService userService;

    // Връщане на всички канали
    @GetMapping
    public ResponseEntity<Object> getAllChannels() {
        List<Channel> channels = channelService.getAllChannels();
        return AppResponse.success()
                .withMessage("Fetched all channels successfully")
                .withData(channels)
                .build();
    }

    // Връщане на канал по ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getChannelById(@PathVariable Integer id) {
        Optional<Channel> channel = channelService.getChannelById(id);
        if (channel.isPresent()) {
            return AppResponse.success()
                    .withMessage("Channel found")
                    .withData(channel.get())
                    .build();
        } else {
            return AppResponse.error()
                    .withMessage("Channel not found")
                    .withCode(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    // Създаване на канал
    @PostMapping
    public ResponseEntity<Object> createChannel(@RequestBody Map<String, String> payload) {
        String name = payload.get("name");
        Integer ownerId = Integer.parseInt(payload.get("ownerId"));
        Channel channel = channelService.createChannel(name, ownerId);
        return AppResponse.success()
                .withMessage("Channel created successfully")
                .withData(channel)
                .build();
    }

    // Добавяне на потребител в канал
    @PostMapping("/{id}/addMember")
    public ResponseEntity<Object> addMemberToChannel(@PathVariable Integer id, @RequestParam Integer userId) {
        Optional<Channel> channelOpt = channelService.getChannelById(id);
        Optional<User> userOpt = userService.getUserById(userId);

        if (channelOpt.isPresent() && userOpt.isPresent()) {
            channelService.addMember(channelOpt.get(), userOpt.get());
            return AppResponse.success()
                    .withMessage("User added to channel successfully")
                    .build();
        } else {
            return AppResponse.error()
                    .withMessage("Channel or User not found")
                    .withCode(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    // Изтриване на канал
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteChannel(@PathVariable Integer id) {
        if (channelService.getChannelById(id).isPresent()) {
            channelService.deleteChannel(id);
            return AppResponse.success()
                    .withMessage("Channel deleted successfully")
                    .build();
        } else {
            return AppResponse.error()
                    .withMessage("Channel not found")
                    .withCode(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @DeleteMapping("/{channelId}/owner/{ownerId}")
    public ResponseEntity<Object> deleteChannelByOwner(@PathVariable Integer channelId, @PathVariable Integer ownerId) {
        try {
            channelService.deleteChannelByOwner(channelId, ownerId);
            return AppResponse.success()
                    .withMessage("Channel deleted successfully")
                    .build();
        } catch (RuntimeException e) {
            return AppResponse.error()
                    .withMessage(e.getMessage())
                    .withCode(HttpStatus.FORBIDDEN)
                    .build();
        }
    }


    @DeleteMapping("/{channelId}/owner/{ownerId}/remove-member/{memberId}")
    public ResponseEntity<Object> removeMemberByOwner(@PathVariable Integer channelId,
                                                      @PathVariable Integer ownerId,
                                                      @PathVariable Integer memberId) {
        try {
            channelService.removeMemberByOwner(channelId, ownerId, memberId);
            return AppResponse.success()
                    .withMessage("Member removed from channel successfully")
                    .build();
        } catch (RuntimeException e) {
            return AppResponse.error()
                    .withMessage(e.getMessage())
                    .withCode(HttpStatus.FORBIDDEN)
                    .build();
        }
    }

    @PostMapping("/{channelId}/owner/{ownerId}/assign-admin")
    public ResponseEntity<Object> assignAdminRole(@PathVariable Integer channelId,
                                                  @PathVariable Integer ownerId,
                                                  @RequestParam Integer userId) {
        try {
            channelService.assignAdminRole(channelId, ownerId, userId);
            return AppResponse.success()
                    .withMessage("Admin role assigned successfully")
                    .build();
        } catch (RuntimeException e) {
            return AppResponse.error()
                    .withMessage(e.getMessage())
                    .withCode(HttpStatus.FORBIDDEN)
                    .build();
        }
    }

    @PostMapping("/{channelId}/admin/{adminId}/add-member")
    public ResponseEntity<Object> addMemberByAdmin(@PathVariable Integer channelId,
                                                   @PathVariable Integer adminId,
                                                   @RequestParam Integer memberId) {
        try {
            channelService.addMemberByAdmin(channelId, adminId, memberId);
            return AppResponse.success()
                    .withMessage("Member added by admin successfully")
                    .build();
        } catch (RuntimeException e) {
            return AppResponse.error()
                    .withMessage(e.getMessage())
                    .withCode(HttpStatus.FORBIDDEN)
                    .build();
        }
    }

    @GetMapping("/paged")
    public ResponseEntity<Object> getAllChannelsPaged(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        Page<Channel> channels = channelService.getAllChannelsPaged(page, size);
        return AppResponse.success()
                .withMessage("Fetched paged channels successfully")
                .withData(channels.getContent())
                .build();
    }


}
