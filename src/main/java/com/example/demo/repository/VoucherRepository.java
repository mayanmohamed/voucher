package com.example.demo.repository;


import com.example.demo.model.VoucherModel;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface VoucherRepository {
    int save(VoucherModel voucherModel);
    int update(VoucherModel voucherModel);
    Optional<VoucherModel> findById(Long id);
    int deleteById(Long id);
    List<VoucherModel> findAll();
    Map getVoucherSerialNumberById(Long id);
    public Map findDiscountAmount(Long id);
}
