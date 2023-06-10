package com.tmblr.blog.controllers;

import com.tmblr.blog.models.Message;
import com.tmblr.blog.models.User;
import com.tmblr.blog.models.dto.MessageDto;
import com.tmblr.blog.services.MessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Controller
public class MessageController {
    private final String uploadPath;
    private final MessageService messageService;

    public MessageController(
            @Value("${upload.path}") String uploadPath,
            MessageService messageService) {
        this.uploadPath = uploadPath;
        this.messageService = messageService;
    }

    @GetMapping("/")
    public String main() {
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String main(@RequestParam(name = "filter", required = false) String filter,
                       @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                       Model model,
                       @AuthenticationPrincipal User user) {

        Page<MessageDto> page = messageService.messageList(pageable, filter, user);

        model.addAttribute("page", page);
        model.addAttribute("url", "/main");
        model.addAttribute("filter", filter);

        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @Valid Message message,
            BindingResult bindingResult,
            Model model,
            @RequestParam(required = false, defaultValue = "") String filter,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        message.setAuthor(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            message.setId(0l);
            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
        } else {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                saveFile(message, file);
            }
            model.addAttribute("message", null);
            messageService.save(message);
        }

        Page<MessageDto> page = messageService.messageList(pageable, filter, user);
        model.addAttribute("page", page);

        return "main";
    }

    @GetMapping("/user-messages/{author}")
    public String userMessages(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User author,
            Model model,
            @RequestParam(required = false) Message message,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<MessageDto> page = messageService.messageListForUser(pageable, currentUser, author);

        model.addAttribute("userBlog", author);
        model.addAttribute("subscriptionsCount", author.getSubscriptions().size());
        model.addAttribute("subscribersCount", author.getSubscribers().size());
        model.addAttribute("isSubscriber", author.getSubscribers().contains(currentUser));
        model.addAttribute("page", page);
        model.addAttribute("message", message);
        model.addAttribute("isCurrentUser", currentUser.equals(author));
        model.addAttribute("url", "/user-messages/" + author.getId());

        return "userMessages";
    }

    @PostMapping("/user-messages/{user}")
    public String updateMessage(
            @RequestHeader(value = HttpHeaders.REFERER, required = false) final String referrer,
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            @RequestParam("id") Message message,
            @RequestParam(value = "text", required = false) String text,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {
        if (message.getAuthor().equals(currentUser)) {
            if (text == null) {
                messageService.delete(message);
                return "redirect:" + referrer;
            }
            if (!text.isEmpty()) {
                message.setText(text);
            }
            if (!tag.isEmpty()) {
                message.setTag(tag);
            }

            saveFile(message, file);

            messageService.save(message);
        }
        return "redirect:/user-messages/" + user.getId();
    }

    @GetMapping("/messages/{message}/like")
    public String like(
            @RequestHeader(value = HttpHeaders.REFERER, required = false) final String referrer,
            @AuthenticationPrincipal User currentUser,
            @PathVariable Message message
    ) {
        Set<User> likes = message.getLikes();

        if (likes.contains(currentUser)) {
            likes.remove(currentUser);
        } else {
            likes.add(currentUser);
        }

        return "redirect:" + referrer;
    }

    private void saveFile(Message message, MultipartFile file) throws IOException {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + "." + file.getOriginalFilename();
        file.transferTo(new File(uploadPath + "/" + resultFilename));
        message.setFilename(resultFilename);
    }
}
