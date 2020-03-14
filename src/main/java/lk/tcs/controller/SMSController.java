package lk.tcs.controller;

import lk.tcs.dao.SMSDao;
import lk.tcs.entity.Messages;
import lk.tcs.util.ModuleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class SMSController {

    @Autowired
    private SMSDao smsdao;


    @RequestMapping(value = "/sms/list", method = RequestMethod.GET, produces = "application/json")
    public List<Messages> list(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isUser(username,password)) {
            return smsdao.list();
        }
        return null;
    }


   /* @RequestMapping(value = "/employees/list/withoutusers",  method = RequestMethod.GET, produces = "application/json")
    public List<Employee> listwithoutusers(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isUser(username,password)) {
            return smsdao.listWithoutUsers();
        }
        return null;
    }*/

   /* @RequestMapping(value = "/sms/list/withuseraccount",  method = RequestMethod.GET, produces = "application/json")
    public List<Messages> listwithuseraccount(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isUser(username,password)) {
            return smsdao.listWithUseraccount();
        }
        return null;
    }*/

    @RequestMapping(value = "/sms/nextsmsno", method = RequestMethod.GET, produces = "application/json")
    public String nextSMSNo(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        if (AuthProvider.isAuthorized(username, password, ModuleList.SMS, AuthProvider.SELECT)) {
            String smscode = smsdao.nextSMSNo();
            return "{\"no\":\"" + smscode + "\"}";
        }
        return null;
    }




    @RequestMapping(value = "/sms", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Messages> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password,@RequestParam("page") int page, @RequestParam("size") int size) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.SMS,AuthProvider.SELECT)) {
            return smsdao.findAll(PageRequest.of(page, size));
        }
        return null;
    }


    @RequestMapping(value = "/sms", params = {"page", "size","subject","employeeId"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Messages> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("subject") String subject, @RequestParam("employeeId") Integer employeeId) {

        // System.out.println(name+"-"+nic+"-"+designationid+"-"+employeestatusid);


        if(AuthProvider.isAuthorized(username,password, ModuleList.SMS,AuthProvider.SELECT)) {

            List<Messages> messages = smsdao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Messages> messagesStream = messages.stream();
/*
            employeestream = employeestream.filter(e -> !(e.getCallingname().equals("Admin")));
*/

            if (employeeId != null)
                messagesStream = messagesStream.filter(m -> m.getEmployeeId().equals(smsdao.getOne(employeeId)));

            messagesStream = messagesStream.filter(m -> m.getSubject().contains(subject));

            List<Messages> messages1 = messagesStream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < messages1.size() ? start + size : messages1.size();
            Page<Messages> emppage = new PageImpl<>(messages1.subList(start, end), PageRequest.of(page, size), messages1.size());

            return emppage;
        }

        return null;

    }


    @RequestMapping(value = "/sms", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Messages messages) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.SMS,AuthProvider.INSERT)) {
            Messages empnic = smsdao.findByCode(messages.getCode());
            if (empnic != null)
                return "Error-Validation : Code Exists";
            else
                try {
                    smsdao.save(messages);
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }



    @RequestMapping(value = "/sms", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody Messages messages) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.SMS,AuthProvider.UPDATE)) {
            Messages ms = smsdao.findByCode(messages.getCode());
            if(ms==null || ms.getCode()==ms.getCode()) {
                try {
                    smsdao.save(messages);
                    return "0";
                }
                catch(Exception e) {
                    return "Error-Updating : "+e.getMessage();
                }
            }
            else {  return "Error-Updating : NIC Exists"; }
        }
        return "Error-Updating : You have no Permission";
    }


    @RequestMapping(value ="/sms", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Messages messages ) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.SMS,AuthProvider.DELETE)) {
            try {
                smsdao.delete(smsdao.getOne(messages.getId()));
                return "0";
            }
            catch(Exception e) {
                return "Error-Deleting : "+e.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }



}
