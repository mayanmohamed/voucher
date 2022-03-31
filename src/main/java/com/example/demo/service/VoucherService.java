package com.example.demo.service;

//import com.example.demo.entity.VoucherEntity;
//import com.example.demo.mapper.VoucherMapper;
import com.example.demo.exception.VoucherException;
import com.example.demo.model.VoucherModel;
import com.example.demo.repository.VoucherRepository;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.http.HttpServletRequest;


@Service
public class VoucherService {

    private final Logger log = LogManager.getLogger(VoucherService.class);

    @Autowired
    HttpServletRequest request;
    @Autowired
    VoucherRepository voucherRepository;

    public void createVoucher( VoucherModel voucherModel) throws VoucherException {
            voucherRepository.save(voucherModel);
    }

    public ResponseEntity<Object> updateVoucher(VoucherModel voucherModel, Long id) throws VoucherException {
        Optional<VoucherModel> entity = voucherRepository.findById(id);
        if(!entity.isPresent()){
            throw new VoucherException("entity does not exist");
            //testing the automated build
        }
        entity.get().setId(id);
        entity.get().setAmount(voucherModel.getAmount());
        entity.get().setSerialNumber(voucherModel.getSerialNumber());
        entity.get().setPercentage(voucherModel.getPercentage());
        voucherRepository.update(entity.get());
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    public ResponseEntity<Object> getVoucherByIdVoucher(Long id) throws VoucherException {
            Optional<VoucherModel> voucherModel = voucherRepository.findById(id);
            if(!voucherModel.isPresent()){
                throw new VoucherException("entity does not exist");
            }
            ThreadContext.put("request-id" , String.valueOf(asUuid(voucherModel.get().getRequestId())));
            log.info("entity successfully returned");
            return new ResponseEntity<>(voucherModel , HttpStatus.OK);
    }

    public ResponseEntity<Object> DeleteVoucherByIdVoucher(Long id) throws VoucherException {
        int r = voucherRepository.deleteById(id);
        if(r==0){
            throw new VoucherException("no entity exists with this id");
        }
        log.info("the entity was successfully deleted");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Object> getAllVouchers() throws VoucherException {
            List<VoucherModel> entity = voucherRepository.findAll();
       // ThreadContext.put("request-id" , String.valueOf(entity.get(i).getRequestId()));
            if(entity.size()==0){
                throw new VoucherException("the list is empty");
            }
            log.info("the list is successfully returned");
            return new ResponseEntity<>(entity , HttpStatus.OK);
    }
    public static UUID asUuid(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        long firstLong = bb.getLong();
        long secondLong = bb.getLong();
        return new UUID(firstLong, secondLong);
    }

    public Map getVoucherSerialNumberById(Long id){
        return voucherRepository.getVoucherSerialNumberById(id);
    }

    public Map findDiscountAmount(Long id){
         return voucherRepository.findDiscountAmount(id);
    }
}
