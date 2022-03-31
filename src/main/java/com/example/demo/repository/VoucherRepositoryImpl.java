package com.example.demo.repository;

import com.example.demo.annotation.LogExecutionTime;
import com.example.demo.model.VoucherModel;
import com.example.demo.util.ByteUUidConversion;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.object.GenericStoredProcedure;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.object.StoredProcedure;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.sql.Types;
import java.util.*;

@Repository
public class VoucherRepositoryImpl implements VoucherRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ByteUUidConversion byteUUidConversion;

    @Autowired
    private DataSource dataSource;

    @LogExecutionTime
    @Override
    public int save(VoucherModel voucherModel) {

        voucherModel.setRequestId(byteUUidConversion.asBytes(UUID.randomUUID()));
        ThreadContext.put("request-id" , String.valueOf(byteUUidConversion.asUuid(voucherModel.getRequestId())));
        return jdbcTemplate.update("INSERT INTO TEST_VOUCHERS (request_id,serial_number,amount,percentage) values(?,?,?,?)" ,
                new Object[] {voucherModel.getRequestId(),voucherModel.getSerialNumber(),voucherModel.getAmount(),voucherModel.getPercentage()});
    }


    @LogExecutionTime
    @Override
    public int update(VoucherModel voucherModel) {
        ThreadContext.put("request-id" , String.valueOf(byteUUidConversion.asUuid(voucherModel.getRequestId())));
        return jdbcTemplate.update("UPDATE TEST_VOUCHERS SET serial_number=?, amount=?, percentage=? WHERE id=?",
                new Object[] {voucherModel.getSerialNumber(),voucherModel.getAmount(),voucherModel.getPercentage(),voucherModel.getId()});
    }

    @Override
    public Optional<VoucherModel> findById(Long id) {
        try {
            VoucherModel voucherModel = jdbcTemplate.queryForObject("SELECT * FROM TEST_VOUCHERS WHERE id=?",
                    BeanPropertyRowMapper.newInstance(VoucherModel.class), id);
            return Optional.ofNullable(voucherModel);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM TEST_VOUCHERS WHERE id=?", id);
    }

    @Override
    public List<VoucherModel> findAll() {
        return jdbcTemplate.query("SELECT * from TEST_VOUCHERS", BeanPropertyRowMapper.newInstance(VoucherModel.class));
    }

    @Override
    public Map<String, Object> getVoucherSerialNumberById(Long id) {

        StoredProcedure procedure = new GenericStoredProcedure();
        procedure.setDataSource(dataSource);
        procedure.setSql("TEST_VOUCHERS_PROCEURE");
        procedure.setFunction(false);

        SqlParameter[] parameters = {
                new SqlParameter(Types.NUMERIC),
                new SqlOutParameter("out_serial_number", Types.VARCHAR)
        };
        procedure.setParameters(parameters);
        procedure.compile();
        Map<String, Object> result = procedure.execute(id);
        System.out.println("Status of moving the person to history: " + result);
        return result;
    }

    @Override
    public Map findDiscountAmount(Long id) {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        SimpleJdbcCall call = new SimpleJdbcCall(template)
                .withFunctionName("TEST_GET_DISCOUNT_VALUE");

        Optional<VoucherModel> voucherModel = findById(id);
        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("amount_val", voucherModel.get().getAmount())
                .addValue("discount_val", voucherModel.get().getPercentage())
                .addValue("in_voucher_id" , id);

        Double discountAmount = call.executeFunction(Double.class, paramMap);
        Map<String , Double> map = new HashMap<>();
        map.put("discountAmount" , discountAmount);
        return map;
    }
}
