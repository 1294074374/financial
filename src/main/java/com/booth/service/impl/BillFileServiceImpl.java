package com.booth.service.impl;

import com.booth.dao.BillDao;
import com.booth.dao.BudgetDao;
import com.booth.pojo.BillVO;
import com.booth.pojo.BudgetVO;
import com.booth.pojo.WechatUser;
import com.booth.service.IBillFileService;
import com.booth.util.RtCode;
import com.booth.util.SpendTypeEnum;
import net.sf.json.JSONObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author laijunlin
 * @date 2021-01-31 14:34
 */
@Service
public class BillFileServiceImpl implements IBillFileService {

    @Autowired
    private BillDao billDao;

    @Autowired
    private BudgetDao budgetDao;

    private static String openId = "ojSJU0aAiUuNWpOqUBBeV5aicvgM";
    private static String filePath = "D:\\账单.xlsx";

    public String loadBillFile() {
        File file = new File(filePath);
        FileInputStream fileInputStream = null;
        List<BillVO> billList = new ArrayList<BillVO>();
        try {
            fileInputStream = new FileInputStream(file);
            XSSFWorkbook workBook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = workBook.getSheetAt(1);
            int rowNumber = sheet.getPhysicalNumberOfRows();
            System.out.println("excel文件一共" + rowNumber + "行");
            for (int i = 1; i < rowNumber; i++) {
                XSSFRow row = sheet.getRow(i);
                BillVO billVO = new BillVO();
                billVO.setOpenId(openId);
                billVO.setTradingDate(getCellValue(row, 0));
                billVO.setSpendType(getCellValue(row, 1));
                billVO.setTransactionType(getCellValue(row, 2));
                billVO.setCounterparty("/");
                billVO.setGoods(getCellValue(row, 4));
                billVO.setBalanceOfPayments(getCellValue(row, 5));
                billVO.setAmt(getCellValue(row, 6));
                billVO.setPayType(getCellValue(row, 7));
                billVO.setStatus(getCellValue(row, 8));
                billVO.setOrderId(getCellValue(row, 9));
                billVO.setMerchantId(getCellValue(row, 10));
                billVO.setRemark(getCellValue(row, 11));
                //checkBill(billVO);
                billList.add(billVO);
            }
            int result = billDao.insertBillList(billList);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return RtCode.SUCCESS;
    }

    
    public String loadMyPagesDate(BillVO billVO) {
        if ("".equals(billVO.getOpenId())) {
            return RtCode.composeRtMsg(RtCode.ABSTKEYPARAMS);
        }
        billVO.setOpenId(openId);
        if(billVO.getSelectDate() == null || "".equals(billVO.getSelectDate())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            billVO.setSelectDate(sdf.format(new Date()));
        }

        // 查询预算
        BudgetVO budgetVO = budgetDao.selectUserMonthBudget(billVO);
        if(budgetVO == null) {
        	budgetVO = new BudgetVO();
        }

        // 总记账天数列表
        List<String> dateList = billDao.selectTrandDateList(billVO);
        // 总记账笔数
        int count = billDao.selectBillCount(billVO);
        // 获取查询月份一共有多少天
        int days = getDaysByYearMonth(billVO.getSelectDate());
        billVO.setStartDate(billVO.getSelectDate() + "-1");
        billVO.setEndDate(billVO.getSelectDate() + "-" + days);
        // 查询收入
        String incomeStr = billDao.selectBalanceBillCount(billVO);
        // 查询支持
        String paymentsStr = billDao.selectPaymentsBillCount(billVO);
        double income = stringToDouble(incomeStr);
        double payments = stringToDouble(paymentsStr);
        double remain = stringToDouble(String.valueOf(income - payments));
        double budget = stringToDouble(budgetVO.getBudget());
        double remainBudget = stringToDouble(String.valueOf(budget - payments));
        List<String> selectDateList = billDao.selectDateList(billVO);

        JSONObject data = new JSONObject();
        data.put("totalDay",dateList.size());
        data.put("totalAmount",count);
        data.put("income",income);
        data.put("spending",payments);
        data.put("remain",remain);
        data.put("budget",budgetVO.getBudget());
        data.put("remainBudget",remainBudget);
        data.put("startMonth",selectDateList.get(0).substring(0,7));
        data.put("endMonth",selectDateList.get(selectDateList.size()-1).substring(0,7));
        data.put("month",String.valueOf(Integer.valueOf(billVO.getSelectDate().substring(6,7))));
        return RtCode.composeRtMsg(RtCode.SUCCESS,data);
    }

    private static double stringToDouble(String str) {
    	if(str == null || "".equals(str)) {
    		return 0.00d;
    	}
        double d = Double.valueOf(str);
        DecimalFormat df = new DecimalFormat("######0.00");
        return Double.valueOf(df.format(d));
    }

    private static int getDaysByYearMonth(String selectDate) {
        if (selectDate == null || "".equals(selectDate)) {
            return -1;
        }
        int year = 0;
        int month = 0;
        year = Integer.valueOf(selectDate.substring(0, 4));
        month = Integer.valueOf(selectDate.substring(5, 7));
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    private void checkBill(BillVO bill) {
        String spendType = bill.getSpendType();
        if (SpendTypeEnum.RESTAURANT.getTypeName().equals(spendType)) {
            bill.setSpendType(String.valueOf(SpendTypeEnum.RESTAURANT.getType()));
        }
        if (SpendTypeEnum.TOP_UP_PAYMENT.getTypeName().equals(spendType)) {
            bill.setSpendType(String.valueOf(SpendTypeEnum.TOP_UP_PAYMENT.getType()));
        }
        if (SpendTypeEnum.CLOTHES.getTypeName().equals(spendType)) {
            bill.setSpendType(String.valueOf(SpendTypeEnum.CLOTHES.getType()));
        }
        if (SpendTypeEnum.TRANSPORTATION.getTypeName().equals(spendType)) {
            bill.setSpendType(String.valueOf(SpendTypeEnum.TRANSPORTATION.getType()));
        }
        if (SpendTypeEnum.BEAUTY_SALON.getTypeName().equals(spendType)) {
            bill.setSpendType(String.valueOf(SpendTypeEnum.BEAUTY_SALON.getType()));
        }
        if (SpendTypeEnum.DAILY_PROVISIONS.getTypeName().equals(spendType)) {
            bill.setSpendType(String.valueOf(SpendTypeEnum.DAILY_PROVISIONS.getType()));
        }
        if (SpendTypeEnum.LIVE_SERVICE.getTypeName().equals(spendType)) {
            bill.setSpendType(String.valueOf(SpendTypeEnum.LIVE_SERVICE.getType()));
        }
        if (SpendTypeEnum.REFUND.getTypeName().equals(spendType)) {
            bill.setSpendType(String.valueOf(SpendTypeEnum.REFUND.getType()));
        }
        if (SpendTypeEnum.WECHAT_TRANSFER.getTypeName().equals(spendType)) {
            bill.setSpendType(String.valueOf(SpendTypeEnum.WECHAT_TRANSFER.getType()));
        }
        if (SpendTypeEnum.TRANSFER_RED_ENVELOPE.getTypeName().equals(spendType)) {
            bill.setSpendType(String.valueOf(SpendTypeEnum.TRANSFER_RED_ENVELOPE.getType()));
        }
        if (SpendTypeEnum.OTHER.getTypeName().equals(spendType)) {
            bill.setSpendType(String.valueOf(SpendTypeEnum.OTHER.getType()));
        }

        String balanceOfPayments = bill.getSpendType();
        if ("收入".equals(balanceOfPayments)) {
            bill.setBalanceOfPayments("1");
        }
        if ("支出".equals(balanceOfPayments)) {
            bill.setBalanceOfPayments("2");
        }
    }

    private String getCellValue(XSSFRow row, int j) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Object cellValue = "";
        if (row.getCell(j) != null) {
            //必须使用switch来获取单元格数据，否则会报错，除非确保execl所有单元格的数据类型一致
            switch (row.getCell(j).getCellType()) {
                //获取布尔型数据
                case Cell.CELL_TYPE_BOOLEAN:
                    cellValue = row.getCell(j).getBooleanCellValue();
                    break;//获取空值
                case Cell.CELL_TYPE_BLANK:
                    cellValue = row.getCell(j).getBooleanCellValue();
                    break;
                //获取错误单元格
                case Cell.CELL_TYPE_ERROR:
                    cellValue = row.getCell(j).getErrorCellString();
                    break;
                //获取数字类型的数据
                case Cell.CELL_TYPE_NUMERIC:
                    Cell cell = row.getCell(j);
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellValue = fmt.format(cell.getDateCellValue());
                    } else {
                        cellValue = row.getCell(j).getNumericCellValue();
                        String result = "" + cellValue;
                        return result;
                    }
                    break;
                //获取字符串
                case Cell.CELL_TYPE_STRING:
                    cellValue = row.getCell(j).getStringCellValue();
                    break;
                default:
                    cellValue = "";
                    break;
            }

        } else {
            cellValue = "";
        }
        return (String) cellValue;
    }
}
