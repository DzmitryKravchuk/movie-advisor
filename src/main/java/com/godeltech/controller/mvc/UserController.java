package com.godeltech.controller.mvc;

import com.godeltech.dto.EvaluationRequest;
import com.godeltech.service.MovieUserEvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public final class UserController {
    private final MovieUserEvaluationService mueService;

    @PostMapping("/user/evaluateMovie")
    public String evaluation(@ModelAttribute("evalRequest") @Valid final EvaluationRequest evalRequest, final Model model) {
        mueService.save(evalRequest);
        return "redirect:/movie/movies/page/1";
    }
}
