package com.example.chatappds.controller;

import com.example.chatappds.dto.ChannelMemberDTO;
import com.example.chatappds.dto.AssignRoleDTO;
import com.example.chatappds.service.ChannelRoleService;
import com.example.chatappds.http.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/channels/roles")
public class ChannelRoleController {

    @Autowired
    private ChannelRoleService channelRoleService;


    @PostMapping
    public ResponseEntity<Object> assignRole(@RequestBody AssignRoleDTO assignRoleDTO) {
        try {
            ChannelMemberDTO channelMemberDTO = channelRoleService.assignRole(assignRoleDTO);
            return AppResponse.success()
                    .withCode(HttpStatus.OK)
                    .withMessage("Role assigned successfully")
                    .withData(channelMemberDTO)
                    .build();
        } catch (Exception e) {
            return AppResponse.error()
                    .withCode(HttpStatus.BAD_REQUEST)
                    .withMessage("Failed to assign role: " + e.getMessage())
                    .build();
        }
    }


    @GetMapping("/{channelId}/members")
    public ResponseEntity<Object> getChannelMembers(@PathVariable Long channelId) {
        try {
            List<ChannelMemberDTO> channelMembers = channelRoleService.getChannelMembers(channelId);
            return AppResponse.success()
                    .withCode(HttpStatus.OK)
                    .withMessage("Channel members retrieved successfully")
                    .withData(channelMembers)
                    .build();
        } catch (Exception e) {
            return AppResponse.error()
                    .withCode(HttpStatus.NOT_FOUND)
                    .withMessage("Failed to retrieve channel members: " + e.getMessage())
                    .build();
        }
    }

    @PutMapping("/{channelId}/members/{userId}/role")
    public ResponseEntity<Object> updateMemberRole(
            @PathVariable Long channelId,
            @PathVariable Long userId,
            @RequestBody AssignRoleDTO assignRoleDTO) {
        try {
            ChannelMemberDTO updatedMember = channelRoleService.updateMemberRole(channelId, userId, assignRoleDTO.getRole());
            return AppResponse.success()
                    .withCode(HttpStatus.OK)
                    .withMessage("Role updated successfully")
                    .withData(updatedMember)
                    .build();
        } catch (Exception e) {
            return AppResponse.error()
                    .withCode(HttpStatus.BAD_REQUEST)
                    .withMessage("Failed to update role: " + e.getMessage())
                    .build();
        }
    }

    @DeleteMapping("/{channelId}/members/{userId}")
    public ResponseEntity<Object> removeMember(@PathVariable Long channelId, @PathVariable Long userId) {
        try {
            channelRoleService.removeMember(channelId, userId);
            return AppResponse.success()
                    .withCode(HttpStatus.OK)
                    .withMessage("Member removed successfully")
                    .build();
        } catch (Exception e) {
            return AppResponse.error()
                    .withCode(HttpStatus.BAD_REQUEST)
                    .withMessage("Failed to remove member: " + e.getMessage())
                    .build();
        }
    }
}
