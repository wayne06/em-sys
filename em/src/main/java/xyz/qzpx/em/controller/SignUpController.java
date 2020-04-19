package xyz.qzpx.em.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.qzpx.em.dataObject.SignUpDO;
import xyz.qzpx.em.service.SignUpService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/signup")
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @PostMapping("/add")
    public SignUpDO add() {
        SignUpDO signUpDO = signUpService.add();
        return signUpDO;
    }

    @PostMapping("/del")
    public void delete(@RequestBody SignUpDO signUpDO) {
        signUpService.delMsg(signUpDO.getId());
    }

    @PostMapping("/getByStatus")
    public List<SignUpDO> get(@RequestBody SignUpDO signUpDO) {
        List<SignUpDO> signUpDOS = signUpService.getByStatus(signUpDO.getStatus());
        return signUpDOS;
    }

    @GetMapping("/collectByStatus")
    public Map<String, List<SignUpDO>> collectByStatus() {
        Map<String, List<SignUpDO>> result = signUpService.collectByStatus();
        return result;
    }

    @PostMapping("/submit")
    public List<Integer> submit(@RequestBody SignUpDO signUpDO) {
        List<Integer> result = signUpService.submit(signUpDO.getId());
        return result;
    }

    @PostMapping("/submit2")
    public void submit2(@RequestBody SignUpDO signUpDO) {
        signUpService.submit2(signUpDO.getId());
    }

    @PostMapping("/approve")
    public void approve(@RequestBody SignUpDO signUpDO) {
        signUpService.approve(signUpDO.getId());
    }

    @PostMapping("/approve2")
    public void approve2(@RequestBody SignUpDO signUpDO) {
        signUpService.approve2(signUpDO.getId());
    }

}
