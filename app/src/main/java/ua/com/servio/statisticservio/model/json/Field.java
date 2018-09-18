package ua.com.servio.statisticservio.model.json;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Field implements Parcelable {

    @Expose
    @SerializedName("CommonID")
    private String commonID;
    @Expose
    @SerializedName("BaseExternalID")
    private String baseExternalID;
    @Expose
    @SerializedName("BaseExternalName")
    private String baseExternalName;
    @Expose
    @SerializedName("OrganizationID")
    private String organizationID;
    @Expose
    @SerializedName("OrganizationName")
    private String organizationName;
    @Expose
    @SerializedName("BuyerID")
    private String buyerID;
    @Expose
    @SerializedName("BuyerName")
    private String buyerName;
    @Expose
    @SerializedName("GuestCount")
    private String guestCount;
    @Expose
    @SerializedName("GuestCountByBaseExternalID")
    private String guestCountByBaseExternalID;
    @Expose
    @SerializedName("GuestCountByOrganizationID")
    private String guestCountByOrganizationID;
    @Expose
    @SerializedName("GuestCountSummary")
    private String guestCountSummary;
    @Expose
    @SerializedName("BillCount")
    private String billCount;
    @Expose
    @SerializedName("BillCountByBaseExternalID")
    private String billCountByBaseExternalID;
    @Expose
    @SerializedName("BillCountByOrganizationID")
    private String billCountByOrganizationID;
    @Expose
    @SerializedName("BillCountSummary")
    private String billCountSummary;
    @Expose
    @SerializedName("BillBaseTotal")
    private String billBaseTotal;
    @Expose
    @SerializedName("BillBaseTotalByBaseExternalID")
    private String billBaseTotalByBaseExternalID;
    @Expose
    @SerializedName("BillBaseTotalByOrganizationID")
    private String billBaseTotalByOrganizationID;
    @Expose
    @SerializedName("BillBaseTotalSummary")
    private String billBaseTotalSummary;
    @Expose
    @SerializedName("BillCountReturn")
    private String billCountReturn;
    @Expose
    @SerializedName("BillCountReturnByBaseExternalID")
    private String billCountReturnByBaseExternalID;
    @Expose
    @SerializedName("BillCountReturnByOrganizationID")
    private String billCountReturnByOrganizationID;
    @Expose
    @SerializedName("BillCountReturnSummary")
    private String billCountReturnSummary;
    @Expose
    @SerializedName("BillTotalReturn")
    private String billTotalReturn;
    @Expose
    @SerializedName("BillTotalReturnByBaseExternalID")
    private String billTotalReturnByBaseExternalID;
    @Expose
    @SerializedName("BillTotalReturnByOrganizationID")
    private String billTotalReturnByOrganizationID;
    @Expose
    @SerializedName("BillTotalReturnSummary")
    private String billTotalReturnSummary;
    @Expose
    @SerializedName("BillOpenedCount")
    private String billOpenedCount;
    @Expose
    @SerializedName("BillOpenedCountByBaseExternalID")
    private String billOpenedCountByBaseExternalID;
    @Expose
    @SerializedName("BillOpenedCountByOrganizationID")
    private String billOpenedCountByOrganizationID;
    @Expose
    @SerializedName("BillOpenedCountSummary")
    private String billOpenedCountSummary;
    @Expose
    @SerializedName("BillOpenedTotal")
    private String billOpenedTotal;
    @Expose
    @SerializedName("BillOpenedTotalByBaseExternalID")
    private String billOpenedTotalByBaseExternalID;
    @Expose
    @SerializedName("BillOpenedTotalByOrganizationID")
    private String billOpenedTotalByOrganizationID;
    @Expose
    @SerializedName("BillOpenedTotalSummary")
    private String billOpenedTotalSummary;
    @Expose
    @SerializedName("SumGuest")
    private String sumGuest;
    @Expose
    @SerializedName("SumGuestByBaseExternalID")
    private String sumGuestByBaseExternalID;
    @Expose
    @SerializedName("SumGuestByOrganizationID")
    private String sumGuestByOrganizationID;
    @Expose
    @SerializedName("SumGuestSummary")
    private String sumGuestSummary;
    @Expose
    @SerializedName("SumBill")
    private String sumBill;
    @Expose
    @SerializedName("SumBillByBaseExternalID")
    private String sumBillByBaseExternalID;
    @Expose
    @SerializedName("SumBillByOrganizationID")
    private String sumBillByOrganizationID;
    @Expose
    @SerializedName("SumBillSummary")
    private String sumBillSummary;
    @Expose
    @SerializedName("GuestBill")
    private String guestBill;
    @Expose
    @SerializedName("GuestBillByBaseExternalID")
    private String guestBillByBaseExternalID;
    @Expose
    @SerializedName("GuestBillByOrganizationID")
    private String guestBillByOrganizationID;
    @Expose
    @SerializedName("GuestBillSummary")
    private String guestBillSummary;
    @Expose
    @SerializedName("DecreaseCount")
    private String decreaseCount;
    @Expose
    @SerializedName("DecreaseCountByBaseExternalID")
    private String decreaseCountByBaseExternalID;
    @Expose
    @SerializedName("DecreaseCountByOrganizationID")
    private String decreaseCountByOrganizationID;
    @Expose
    @SerializedName("DecreaseCountSummary")
    private String decreaseCountSummary;
    @Expose
    @SerializedName("DecreaseSum")
    private String decreaseSum;
    @Expose
    @SerializedName("DecreaseSumByBaseExternalID")
    private String decreaseSumByBaseExternalID;
    @Expose
    @SerializedName("DecreaseSumByOrganizationID")
    private String decreaseSumByOrganizationID;
    @Expose
    @SerializedName("DecreaseSumSummary")
    private String decreaseSumSummary;
    @Expose
    @SerializedName("Tax1")
    private String tax1;
    @Expose
    @SerializedName("Tax1ByBaseExternalID")
    private String tax1ByBaseExternalID;
    @Expose
    @SerializedName("Tax1ByOrganizationID")
    private String tax1ByOrganizationID;
    @Expose
    @SerializedName("Tax1Summary")
    private String tax1Summary;
    @Expose
    @SerializedName("Tax2")
    private String tax2;
    @Expose
    @SerializedName("Tax2ByBaseExternalID")
    private String tax2ByBaseExternalID;
    @Expose
    @SerializedName("Tax2ByOrganizationID")
    private String tax2ByOrganizationID;
    @Expose
    @SerializedName("Tax2Summary")
    private String tax2Summary;
    @Expose
    @SerializedName("OrderPrepareTime")
    private String orderPrepareTime;
    @Expose
    @SerializedName("BillItemCount")
    private String billItemCount;
    @Expose
    @SerializedName("AVGPrepareTimeByBaseExternalID")
    private String aVGPrepareTimeByBaseExternalID;
    @Expose
    @SerializedName("AVGPrepareTimeByOrganizationID")
    private String aVGPrepareTimeByOrganizationID;
    @Expose
    @SerializedName("aVGPrepareTimeSummary")
    private String aVGPrepareTimeSummary;

    //GroupDiscount
    @Expose
    @SerializedName("DiscountGroupName")
    private String discountGroupName;
    @Expose
    @SerializedName("DiscountSum")
    private String discountSum;
    @Expose
    @SerializedName("BonusSum")
    private String bonusSum;

    //ManualDiscount
    @Expose
    @SerializedName("DiscountPercent")
    private String discountPercent;

    //Payment
    @Expose
    @SerializedName("PaymentTypeName")
    private String paymentTypeName;
    @Expose
    @SerializedName("PaymentFiscalTypeName")
    private String paymentFiscalTypeName;
    @Expose
    @SerializedName("Accrual")
    private String accrual;
    @Expose
    @SerializedName("Discount")
    private String discount;
    @Expose
    @SerializedName("Payment")
    private String payment;

    //Section
    @Expose
    @SerializedName("SectionName")
    private String sectionName;
    @Expose
    @SerializedName("Amount")
    private String amount;

    //Hall
    @Expose
    @SerializedName("PlaceGroupShort")
    private String placeGroupShort;

    //User
    @Expose
    @SerializedName("UserName")
    private String userName;


    public Field(String commonID, String baseExternalID, String baseExternalName,
                 String organizationID, String organizationName, String buyerID,
                 String buyerName, String guestCount, String guestCountByBaseExternalID,
                 String guestCountByOrganizationID, String guestCountSummary,
                 String billCount, String billCountByBaseExternalID,
                 String billCountByOrganizationID, String billCountSummary,
                 String billBaseTotal, String billBaseTotalByBaseExternalID,
                 String billBaseTotalByOrganizationID, String billBaseTotalSummary,
                 String billCountReturn, String billCountReturnByBaseExternalID,
                 String billCountReturnByOrganizationID, String billCountReturnSummary,
                 String billTotalReturn, String billTotalReturnByBaseExternalID,
                 String billTotalReturnByOrganizationID, String billTotalReturnSummary,
                 String billOpenedCount, String billOpenedCountByBaseExternalID,
                 String billOpenedCountByOrganizationID, String billOpenedCountSummary,
                 String billOpenedTotal, String billOpenedTotalByBaseExternalID,
                 String billOpenedTotalByOrganizationID, String billOpenedTotalSummary,
                 String sumGuest, String sumGuestByBaseExternalID,
                 String sumGuestByOrganizationID, String sumGuestSummary,
                 String sumBill, String sumBillByBaseExternalID,
                 String sumBillByOrganizationID, String sumBillSummary,
                 String guestBill, String guestBillByBaseExternalID,
                 String guestBillByOrganizationID, String guestBillSummary,
                 String decreaseCount, String decreaseCountByBaseExternalID,
                 String decreaseCountByOrganizationID, String decreaseCountSummary,
                 String decreaseSum, String decreaseSumByBaseExternalID,
                 String decreaseSumByOrganizationID, String decreaseSumSummary,
                 String tax1, String tax1ByBaseExternalID, String tax1ByOrganizationID,
                 String tax1Summary, String tax2, String tax2ByBaseExternalID,
                 String tax2ByOrganizationID, String tax2Summary,
                 String orderPrepareTime, String billItemCount,
                 String aVGPrepareTimeByBaseExternalID,
                 String aVGPrepareTimeByOrganizationID,
                 String aVGPrepareTimeSummary, String discountGroupName,
                 String discountSum, String bonusSum, String discountPercent,
                 String paymentTypeName, String paymentFiscalTypeName,
                 String accrual, String discount, String payment, String sectionName,
                 String amount, String placeGroupShort, String userName) {
        this.commonID = commonID;
        this.baseExternalID = baseExternalID;
        this.baseExternalName = baseExternalName;
        this.organizationID = organizationID;
        this.organizationName = organizationName;
        this.buyerID = buyerID;
        this.buyerName = buyerName;
        this.guestCount = guestCount;
        this.guestCountByBaseExternalID = guestCountByBaseExternalID;
        this.guestCountByOrganizationID = guestCountByOrganizationID;
        this.guestCountSummary = guestCountSummary;
        this.billCount = billCount;
        this.billCountByBaseExternalID = billCountByBaseExternalID;
        this.billCountByOrganizationID = billCountByOrganizationID;
        this.billCountSummary = billCountSummary;
        this.billBaseTotal = billBaseTotal;
        this.billBaseTotalByBaseExternalID = billBaseTotalByBaseExternalID;
        this.billBaseTotalByOrganizationID = billBaseTotalByOrganizationID;
        this.billBaseTotalSummary = billBaseTotalSummary;
        this.billCountReturn = billCountReturn;
        this.billCountReturnByBaseExternalID = billCountReturnByBaseExternalID;
        this.billCountReturnByOrganizationID = billCountReturnByOrganizationID;
        this.billCountReturnSummary = billCountReturnSummary;
        this.billTotalReturn = billTotalReturn;
        this.billTotalReturnByBaseExternalID = billTotalReturnByBaseExternalID;
        this.billTotalReturnByOrganizationID = billTotalReturnByOrganizationID;
        this.billTotalReturnSummary = billTotalReturnSummary;
        this.billOpenedCount = billOpenedCount;
        this.billOpenedCountByBaseExternalID = billOpenedCountByBaseExternalID;
        this.billOpenedCountByOrganizationID = billOpenedCountByOrganizationID;
        this.billOpenedCountSummary = billOpenedCountSummary;
        this.billOpenedTotal = billOpenedTotal;
        this.billOpenedTotalByBaseExternalID = billOpenedTotalByBaseExternalID;
        this.billOpenedTotalByOrganizationID = billOpenedTotalByOrganizationID;
        this.billOpenedTotalSummary = billOpenedTotalSummary;
        this.sumGuest = sumGuest;
        this.sumGuestByBaseExternalID = sumGuestByBaseExternalID;
        this.sumGuestByOrganizationID = sumGuestByOrganizationID;
        this.sumGuestSummary = sumGuestSummary;
        this.sumBill = sumBill;
        this.sumBillByBaseExternalID = sumBillByBaseExternalID;
        this.sumBillByOrganizationID = sumBillByOrganizationID;
        this.sumBillSummary = sumBillSummary;
        this.guestBill = guestBill;
        this.guestBillByBaseExternalID = guestBillByBaseExternalID;
        this.guestBillByOrganizationID = guestBillByOrganizationID;
        this.guestBillSummary = guestBillSummary;
        this.decreaseCount = decreaseCount;
        this.decreaseCountByBaseExternalID = decreaseCountByBaseExternalID;
        this.decreaseCountByOrganizationID = decreaseCountByOrganizationID;
        this.decreaseCountSummary = decreaseCountSummary;
        this.decreaseSum = decreaseSum;
        this.decreaseSumByBaseExternalID = decreaseSumByBaseExternalID;
        this.decreaseSumByOrganizationID = decreaseSumByOrganizationID;
        this.decreaseSumSummary = decreaseSumSummary;
        this.tax1 = tax1;
        this.tax1ByBaseExternalID = tax1ByBaseExternalID;
        this.tax1ByOrganizationID = tax1ByOrganizationID;
        this.tax1Summary = tax1Summary;
        this.tax2 = tax2;
        this.tax2ByBaseExternalID = tax2ByBaseExternalID;
        this.tax2ByOrganizationID = tax2ByOrganizationID;
        this.tax2Summary = tax2Summary;
        this.orderPrepareTime = orderPrepareTime;
        this.billItemCount = billItemCount;
        this.aVGPrepareTimeByBaseExternalID = aVGPrepareTimeByBaseExternalID;
        this.aVGPrepareTimeByOrganizationID = aVGPrepareTimeByOrganizationID;
        this.aVGPrepareTimeSummary = aVGPrepareTimeSummary;
        this.discountGroupName = discountGroupName;
        this.discountSum = discountSum;
        this.bonusSum = bonusSum;
        this.discountPercent = discountPercent;
        this.paymentTypeName = paymentTypeName;
        this.paymentFiscalTypeName = paymentFiscalTypeName;
        this.accrual = accrual;
        this.discount = discount;
        this.payment = payment;
        this.sectionName = sectionName;
        this.amount = amount;
        this.placeGroupShort = placeGroupShort;
        this.userName = userName;
    }

    public String getCommonID() {
        return commonID;
    }

    public String getBaseExternalID() {
        return baseExternalID;
    }

    public String getBaseExternalName() {
        return baseExternalName;
    }

    public String getOrganizationID() {
        return organizationID;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getBuyerID() {
        return buyerID;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getGuestCount() {
        return guestCount;
    }

    public String getGuestCountByBaseExternalID() {
        return guestCountByBaseExternalID;
    }

    public String getGuestCountByOrganizationID() {
        return guestCountByOrganizationID;
    }

    public String getGuestCountSummary() {
        return guestCountSummary;
    }

    public String getBillCount() {
        return billCount;
    }

    public String getBillCountByBaseExternalID() {
        return billCountByBaseExternalID;
    }

    public String getBillCountByOrganizationID() {
        return billCountByOrganizationID;
    }

    public String getBillCountSummary() {
        return billCountSummary;
    }

    public String getBillBaseTotal() {
        return billBaseTotal;
    }

    public String getBillBaseTotalByBaseExternalID() {
        return billBaseTotalByBaseExternalID;
    }

    public String getBillBaseTotalByOrganizationID() {
        return billBaseTotalByOrganizationID;
    }

    public String getBillBaseTotalSummary() {
        return billBaseTotalSummary;
    }

    public String getBillCountReturn() {
        return billCountReturn;
    }

    public String getBillCountReturnByBaseExternalID() {
        return billCountReturnByBaseExternalID;
    }

    public String getBillCountReturnByOrganizationID() {
        return billCountReturnByOrganizationID;
    }

    public String getBillCountReturnSummary() {
        return billCountReturnSummary;
    }

    public String getBillTotalReturn() {
        return billTotalReturn;
    }

    public String getBillTotalReturnByBaseExternalID() {
        return billTotalReturnByBaseExternalID;
    }

    public String getBillTotalReturnByOrganizationID() {
        return billTotalReturnByOrganizationID;
    }

    public String getBillTotalReturnSummary() {
        return billTotalReturnSummary;
    }

    public String getBillOpenedCount() {
        return billOpenedCount;
    }

    public String getBillOpenedCountByBaseExternalID() {
        return billOpenedCountByBaseExternalID;
    }

    public String getBillOpenedCountByOrganizationID() {
        return billOpenedCountByOrganizationID;
    }

    public String getBillOpenedCountSummary() {
        return billOpenedCountSummary;
    }

    public String getBillOpenedTotal() {
        return billOpenedTotal;
    }

    public String getBillOpenedTotalByBaseExternalID() {
        return billOpenedTotalByBaseExternalID;
    }

    public String getBillOpenedTotalByOrganizationID() {
        return billOpenedTotalByOrganizationID;
    }

    public String getBillOpenedTotalSummary() {
        return billOpenedTotalSummary;
    }

    public String getSumGuest() {
        return sumGuest;
    }

    public String getSumGuestByBaseExternalID() {
        return sumGuestByBaseExternalID;
    }

    public String getSumGuestByOrganizationID() {
        return sumGuestByOrganizationID;
    }

    public String getSumGuestSummary() {
        return sumGuestSummary;
    }

    public String getSumBill() {
        return sumBill;
    }

    public String getSumBillByBaseExternalID() {
        return sumBillByBaseExternalID;
    }

    public String getSumBillByOrganizationID() {
        return sumBillByOrganizationID;
    }

    public String getSumBillSummary() {
        return sumBillSummary;
    }

    public String getGuestBill() {
        return guestBill;
    }

    public String getGuestBillByBaseExternalID() {
        return guestBillByBaseExternalID;
    }

    public String getGuestBillByOrganizationID() {
        return guestBillByOrganizationID;
    }

    public String getGuestBillSummary() {
        return guestBillSummary;
    }

    public String getDecreaseCount() {
        return decreaseCount;
    }

    public String getDecreaseCountByBaseExternalID() {
        return decreaseCountByBaseExternalID;
    }

    public String getDecreaseCountByOrganizationID() {
        return decreaseCountByOrganizationID;
    }

    public String getDecreaseCountSummary() {
        return decreaseCountSummary;
    }

    public String getDecreaseSum() {
        return decreaseSum;
    }

    public String getDecreaseSumByBaseExternalID() {
        return decreaseSumByBaseExternalID;
    }

    public String getDecreaseSumByOrganizationID() {
        return decreaseSumByOrganizationID;
    }

    public String getDecreaseSumSummary() {
        return decreaseSumSummary;
    }

    public String getTax1() {
        return tax1;
    }

    public String getTax1ByBaseExternalID() {
        return tax1ByBaseExternalID;
    }

    public String getTax1ByOrganizationID() {
        return tax1ByOrganizationID;
    }

    public String getTax1Summary() {
        return tax1Summary;
    }

    public String getTax2() {
        return tax2;
    }

    public String getTax2ByBaseExternalID() {
        return tax2ByBaseExternalID;
    }

    public String getTax2ByOrganizationID() {
        return tax2ByOrganizationID;
    }

    public String getTax2Summary() {
        return tax2Summary;
    }

    public String getOrderPrepareTime() {
        return orderPrepareTime;
    }

    public String getBillItemCount() {
        return billItemCount;
    }

    public String getaVGPrepareTimeByBaseExternalID() {
        return aVGPrepareTimeByBaseExternalID;
    }

    public String getaVGPrepareTimeByOrganizationID() {
        return aVGPrepareTimeByOrganizationID;
    }

    public String getaVGPrepareTimeSummary() {
        return aVGPrepareTimeSummary;
    }

    public String getDiscountGroupName() {
        return discountGroupName;
    }

    public String getDiscountSum() {
        return discountSum;
    }

    public String getBonusSum() {
        return bonusSum;
    }

    public String getDiscountPercent() {
        return discountPercent;
    }

    public String getPaymentTypeName() {
        return paymentTypeName;
    }

    public String getPaymentFiscalTypeName() {
        return paymentFiscalTypeName;
    }

    public String getAccrual() {
        return accrual;
    }

    public String getDiscount() {
        return discount;
    }

    public String getPayment() {
        return payment;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getAmount() {
        return amount;
    }

    public String getPlaceGroupShort() {
        return placeGroupShort;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.commonID);
        dest.writeString(this.baseExternalID);
        dest.writeString(this.baseExternalName);
        dest.writeString(this.organizationID);
        dest.writeString(this.organizationName);
        dest.writeString(this.buyerID);
        dest.writeString(this.buyerName);
        dest.writeString(this.guestCount );
        dest.writeString(this.guestCountByBaseExternalID);
        dest.writeString(this.guestCountByOrganizationID);
        dest.writeString(this.guestCountSummary);
        dest.writeString(this.billCount);
        dest.writeString(this.billCountByBaseExternalID);
        dest.writeString(this.billCountByOrganizationID);
        dest.writeString(this.billCountSummary);
        dest.writeString(this.billBaseTotal);
        dest.writeString(this.billBaseTotalByBaseExternalID);
        dest.writeString(this.billBaseTotalByOrganizationID);
        dest.writeString(this.billBaseTotalSummary);
        dest.writeString(this.billCountReturn);
        dest.writeString(this.billCountReturnByBaseExternalID);
        dest.writeString(this.billCountReturnByOrganizationID);
        dest.writeString(this.billCountReturnSummary);
        dest.writeString(this.billTotalReturn);
        dest.writeString(this.billTotalReturnByBaseExternalID);
        dest.writeString(this.billTotalReturnByOrganizationID);
        dest.writeString(this.billTotalReturnSummary);
        dest.writeString(this.billOpenedCount);
        dest.writeString(this.billOpenedCountByBaseExternalID);
        dest.writeString(this.billOpenedCountByOrganizationID);
        dest.writeString(this.billOpenedCountSummary);
        dest.writeString(this.billOpenedTotal);
        dest.writeString(this.billOpenedTotalByBaseExternalID);
        dest.writeString(this.billOpenedTotalByOrganizationID);
        dest.writeString(this.billOpenedTotalSummary);
        dest.writeString(this.sumGuest);
        dest.writeString(this.sumGuestByBaseExternalID);
        dest.writeString(this.sumGuestByOrganizationID);
        dest.writeString(this.sumGuestSummary);
        dest.writeString(this.sumBill);
        dest.writeString(this.sumBillByBaseExternalID);
        dest.writeString(this.sumBillByOrganizationID);
        dest.writeString(this.sumBillSummary);
        dest.writeString(this.guestBill);
        dest.writeString(this.guestBillByBaseExternalID);
        dest.writeString(this.guestBillByOrganizationID);
        dest.writeString(this.guestBillSummary);
        dest.writeString(this.decreaseCount);
        dest.writeString(this.decreaseCountByBaseExternalID);
        dest.writeString(this.decreaseCountByOrganizationID);
        dest.writeString(this.decreaseCountSummary);
        dest.writeString(this.decreaseSum);
        dest.writeString(this.decreaseSumByBaseExternalID);
        dest.writeString(this.decreaseSumByOrganizationID);
        dest.writeString(this.decreaseSumSummary);
        dest.writeString(this.tax1);
        dest.writeString(this.tax1ByBaseExternalID);
        dest.writeString(this.tax1ByOrganizationID);
        dest.writeString(this.tax1Summary);
        dest.writeString(this.tax2);
        dest.writeString(this.tax2ByBaseExternalID);
        dest.writeString(this.tax2ByOrganizationID);
        dest.writeString(this.tax2Summary);
        dest.writeString(this.orderPrepareTime);
        dest.writeString(this.billItemCount);
        dest.writeString(this.aVGPrepareTimeByBaseExternalID);
        dest.writeString(this.aVGPrepareTimeByOrganizationID);
        dest.writeString(this.aVGPrepareTimeSummary);
        dest.writeString(this.discountGroupName);
        dest.writeString(this.discountSum);
        dest.writeString(this.bonusSum);
        dest.writeString(this.discountPercent);
        dest.writeString(this.paymentTypeName);
        dest.writeString(this.paymentFiscalTypeName);
        dest.writeString(this.accrual);
        dest.writeString(this.discount);
        dest.writeString(this.payment);
        dest.writeString(this.sectionName);
        dest.writeString(this.amount);
        dest.writeString(this.placeGroupShort);
        dest.writeString(this.userName);
    }

    protected Field(Parcel in) {
        this.commonID = in.readString();
        this.baseExternalID = in.readString();
        this.baseExternalName = in.readString();
        this.organizationID = in.readString();
        this.organizationName = in.readString();
        this.buyerID = in.readString();
        this.buyerName = in.readString();
        this.guestCount = in.readString();
        this.guestCountByBaseExternalID = in.readString();
        this.guestCountByOrganizationID = in.readString();
        this.guestCountSummary = in.readString();
        this.billCount = in.readString();
        this.billCountByBaseExternalID = in.readString();
        this.billCountByOrganizationID = in.readString();
        this.billCountSummary = in.readString();
        this.billBaseTotal = in.readString();
        this.billBaseTotalByBaseExternalID = in.readString();
        this.billBaseTotalByOrganizationID = in.readString();
        this.billBaseTotalSummary = in.readString();
        this.billCountReturn = in.readString();
        this.billCountReturnByBaseExternalID = in.readString();
        this.billCountReturnByOrganizationID = in.readString();
        this.billCountReturnSummary = in.readString();
        this.billTotalReturn = in.readString();
        this.billTotalReturnByBaseExternalID = in.readString();
        this.billTotalReturnByOrganizationID = in.readString();
        this.billTotalReturnSummary = in.readString();
        this.billOpenedCount = in.readString();
        this.billOpenedCountByBaseExternalID = in.readString();
        this.billOpenedCountByOrganizationID = in.readString();
        this.billOpenedCountSummary = in.readString();
        this.billOpenedTotal = in.readString();
        this.billOpenedTotalByBaseExternalID = in.readString();
        this.billOpenedTotalByOrganizationID = in.readString();
        this.billOpenedTotalSummary = in.readString();
        this.sumGuest = in.readString();
        this.sumGuestByBaseExternalID = in.readString();
        this.sumGuestByOrganizationID = in.readString();
        this.sumGuestSummary = in.readString();
        this.sumBill = in.readString();
        this.sumBillByBaseExternalID = in.readString();
        this.sumBillByOrganizationID = in.readString();
        this.sumBillSummary = in.readString();
        this.guestBill = in.readString();
        this.guestBillByBaseExternalID = in.readString();
        this.guestBillByOrganizationID = in.readString();
        this.guestBillSummary = in.readString();
        this.decreaseCount = in.readString();
        this.decreaseCountByBaseExternalID = in.readString();
        this.decreaseCountByOrganizationID = in.readString();
        this.decreaseCountSummary = in.readString();
        this.decreaseSum = in.readString();
        this.decreaseSumByBaseExternalID = in.readString();
        this.decreaseSumByOrganizationID = in.readString();
        this.decreaseSumSummary = in.readString();
        this.tax1 = in.readString();
        this.tax1ByBaseExternalID = in.readString();
        this.tax1ByOrganizationID = in.readString();
        this.tax1Summary = in.readString();
        this.tax2 = in.readString();
        this.tax2ByBaseExternalID = in.readString();
        this.tax2ByOrganizationID = in.readString();
        this.tax2Summary = in.readString();
        this.orderPrepareTime = in.readString();
        this.billItemCount = in.readString();
        this.aVGPrepareTimeByBaseExternalID = in.readString();
        this.aVGPrepareTimeByOrganizationID = in.readString();
        this.aVGPrepareTimeSummary = in.readString();
        this.discountGroupName = in.readString();
        this.discountSum = in.readString();
        this.bonusSum = in.readString();
        this.discountPercent = in.readString();
        this.paymentTypeName = in.readString();
        this.paymentFiscalTypeName = in.readString();
        this.accrual = in.readString();
        this.discount = in.readString();
        this.payment = in.readString();
        this.sectionName = in.readString();
        this.amount = in.readString();
        this.placeGroupShort = in.readString();
        this.userName = in.readString();
    }

    public static final Creator<Field> CREATOR = new Creator<Field>() {
        @Override
        public Field createFromParcel(Parcel source) {
            return new Field(source);
        }

        @Override
        public Field[] newArray(int size) {
            return new Field[size];
        }
    };

    @Override
    public String toString() {
        return "";
    }
}

