package com.popbill.example.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.popbill.api.AccountCheckInfo;
import com.popbill.api.AccountCheckService;
import com.popbill.api.ChargeInfo;
import com.popbill.api.PopbillException;

@Controller
@RequestMapping("AccountCheckService")
public class AccountCheckController {
    @Autowired
    private AccountCheckService accountCheckService;

    // 팝빌회원 사업자번호
    private String testCorpNum="1234567890";

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        return "AccountCheck/index";
    }

    @RequestMapping(value = "checkAccountInfo", method = RequestMethod.GET)
    public String checkAccountInfo(Model m) {
        /*
         * 1건의 예금주성명을 조회합니다. 
         * - https://docs.popbill.com/accountcheck/java/api#CheckAccountInfo
         */

        // 기관코드
        String BankCode = "0004";

        // 계좌번호
        String AccountNumber = "9432451175834";

        try {

            AccountCheckInfo accountInfo = accountCheckService.CheckAccountInfo(testCorpNum, BankCode, AccountNumber);

            m.addAttribute("AccountInfo", accountInfo);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "AccountCheck/checkAccountInfo";
    }

    @RequestMapping(value = "getUnitCost", method = RequestMethod.GET)
    public String getUnitCost(Model m) {
        /*
         * 예금주 성명 조회시 과금되는 포인트 단가를 확인합니다. 
         * - https://docs.popbill.com/accountcheck/java/api#GetUnitCost
         */

        try {

            float unitCost = accountCheckService.getUnitCost(testCorpNum);

            m.addAttribute("Result", unitCost);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "result";
    }

    @RequestMapping(value = "getChargeInfo", method = RequestMethod.GET)
    public String chargeInfo(Model m) {
        /*
         * 예금주조회 API 서비스 과금정보를 확인합니다. 
         * - https://docs.popbill.com/accountcheck/java/api#GetChargeInfo
         */

        try {
            ChargeInfo chrgInfo = accountCheckService.getChargeInfo(testCorpNum);
            m.addAttribute("ChargeInfo", chrgInfo);

        } catch (PopbillException e) {
            m.addAttribute("Exception", e);
            return "exception";
        }

        return "getChargeInfo";
    }

}
