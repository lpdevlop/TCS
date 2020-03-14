package lk.tcs.controller;

import lk.tcs.dao.EmployeeDao;
import lk.tcs.dao.WarrantyStatusDao;
import lk.tcs.dao.WarrantyserviceDao;

import lk.tcs.entity.Warrantyservice;
import lk.tcs.entity.Warrantyservicestatus;
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
public class WarrantyserviceController {

    
    @Autowired
    private WarrantyserviceDao warrantyservicedao;

    @Autowired
    private WarrantyStatusDao warrantyStatusDao;

    @Autowired
    private EmployeeDao employeeDao;



    @RequestMapping(value = "/warrantyservice", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Warrantyservice> findAll(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password, @RequestParam("page") int page, @RequestParam("size") int size) {
        if (AuthProvider.isAuthorized(username, password, ModuleList.WARRANTYSERVICE, AuthProvider.SELECT)) {
            return warrantyservicedao.findAll(PageRequest.of(page, size));
        }
        return null;
    }


    @RequestMapping(value = "/warrantyservice/nextwarrantyno", method = RequestMethod.GET, produces = "application/json")
    public String nextWrrantyno(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        if (AuthProvider.isAuthorized(username, password, ModuleList.WARRANTYSERVICE, AuthProvider.SELECT)) {
            String warrantycode = warrantyservicedao.nextWrrantyno();
            return "{\"no\":\"" + warrantycode + "\"}";
        }
        return null;
    }

    @RequestMapping(value = "/warrantyservice", method = RequestMethod.POST)
    public String add(@CookieValue(value = "username", required = false) String username, @CookieValue(value = "password", required = false) String password, @Validated @RequestBody Warrantyservice warrantyservice) {

        if (AuthProvider.isAuthorized(username, password, ModuleList.WARRANTYSERVICE, AuthProvider.INSERT)) {
            Warrantyservice ws = warrantyservicedao.findbyCode(warrantyservice.getCode());
            if (ws != null) {
                return "Error validation" + ws.getCode();
            }
            try {

                warrantyservicedao.save(warrantyservice);
                return "0";
            } catch (Exception s) {
                s.printStackTrace();
                return "Error-Saving : " + s.getMessage();


            }
        }
        return "Error-Saving : You have no Permission";

    }



    @RequestMapping(value = "/warrantyservice", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody Warrantyservice warrantyservice){

        if(AuthProvider.isAuthorized(username,password,ModuleList.WARRANTYSERVICE,AuthProvider.UPDATE)) {
            try {
             /*   for (Disposalitem dpitem : disposal.getDisposalitemList()) {
                    dpitem.setDisposalId(disposal);
                }
*/
                warrantyservicedao.save(warrantyservice);
                return "0";
            } catch (Exception e) {
                return "Error-Update : " + e.getMessage();
            }
        } return "Error-Updating : You have no Permission";
    }



    @RequestMapping(value = "/warrantyservice", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody Warrantyservice warrantyservice){

        if(AuthProvider.isAuthorized(username,password,ModuleList.WARRANTYSERVICE,AuthProvider.DELETE)) {
            try {

            /*    Warrantyservice ws =  warrantyservicedao.getOne(warrantyservicedao.getId());
                dp.getDisposalitemList().remove(dp);*/

                warrantyservicedao.delete(warrantyservice);

                return "0";
            } catch (Exception e) {
                return "Error-Delete : " + e.getMessage();
            }
        } return "Error-Deleting : You have no Permission";
    }


    @RequestMapping(value = "/warrantyservice", params = {"page", "size","code","employeeId","warrantyservicestatusId"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Warrantyservice> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("code") String code, @RequestParam("employeeId") Integer employeeId, @RequestParam("warrantyservicestatusId") Integer warrantyservicestatusId) {


        if (AuthProvider.isAuthorized(username, password, ModuleList.WARRANTYSERVICE,AuthProvider.SELECT)) {
            List<Warrantyservice> warrantyservice = warrantyservicedao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Warrantyservice> warrantyserviceStream = warrantyservice.stream();


            if (warrantyservicestatusId != null)
                warrantyserviceStream = warrantyserviceStream.filter(w -> w.getWarrantyservicestatusId().equals(warrantyStatusDao.getOne(warrantyservicestatusId)));
            if(employeeId !=null)
                warrantyserviceStream = warrantyserviceStream.filter(w -> w.getEmployeeId().equals(employeeDao.getOne(employeeId)));
            warrantyserviceStream = warrantyserviceStream.filter(w -> w.getCode().contains(code));


            List<Warrantyservice> warrantyList = warrantyserviceStream.collect(Collectors.toList());
            int start = page * size;
            int end = start + size < warrantyList.size() ? start + size : warrantyList.size();
            Page<Warrantyservice> wapage = new PageImpl<>(warrantyList.subList(start, end), PageRequest.of(page, size), warrantyList.size());

            return wapage;

        }
        return null;
    }

}





