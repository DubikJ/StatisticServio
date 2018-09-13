package ua.com.servio.statisticservio.model.json;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadResponse {

    @Expose
    @SerializedName("ReportName")
    private String reportName;
    @Expose
    @SerializedName("BaseExternalID")
    private String baseExternalID;
    @Expose
    @SerializedName("OrganizationID")
    private String organizationID;
    @Expose
    @SerializedName("DateIn")
    private String dateIn;
    @Expose
    @SerializedName("DateOut")
    private String dateOut;
    @Expose
    @SerializedName("SessionID")
    private String sessionID;
    @Expose
    @SerializedName("StationID")
    private String stationID;
    @Expose
    @SerializedName("SectionID")
    private String sectionID;
    @Expose
    @SerializedName("PaymentType")
    private String paymentType;
    @Expose
    @SerializedName("PersonID")
    private String personID;
    @Expose
    @SerializedName("ObjectView")
    private String objectView;
    @Expose
    @SerializedName("ObjectType")
    private String objectType;
    @Expose
    @SerializedName("ChartDisplay")
    private String chartDisplay;
    @Expose
    @SerializedName("SortDirection")
    private String sortDirection;
    @Expose
    @SerializedName("GroupCase")
    private String groupCase;
    @Expose
    @SerializedName("ReportBlocks")
    private String reportBlocks;

    public UploadResponse(String reportName, String baseExternalID, String organizationID,
                          String dateIn, String dateOut, String sessionID, String stationID,
                          String sectionID, String paymentType, String personID, String objectView,
                          String objectType, String chartDisplay, String sortDirection,
                          String groupCase, String reportBlocks) {
        this.reportName = reportName;
        this.baseExternalID = baseExternalID;
        this.organizationID = organizationID;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
        this.sessionID = sessionID;
        this.stationID = stationID;
        this.sectionID = sectionID;
        this.paymentType = paymentType;
        this.personID = personID;
        this.objectView = objectView;
        this.objectType = objectType;
        this.chartDisplay = chartDisplay;
        this.sortDirection = sortDirection;
        this.groupCase = groupCase;
        this.reportBlocks = reportBlocks;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getBaseExternalID() {
        return baseExternalID;
    }

    public void setBaseExternalID(String baseExternalID) {
        this.baseExternalID = baseExternalID;
    }

    public String getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(String organizationID) {
        this.organizationID = organizationID;
    }

    public String getDateIn() {
        return dateIn;
    }

    public void setDateIn(String dateIn) {
        this.dateIn = dateIn;
    }

    public String getDateOut() {
        return dateOut;
    }

    public void setDateOut(String dateOut) {
        this.dateOut = dateOut;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public String getSectionID() {
        return sectionID;
    }

    public void setSectionID(String sectionID) {
        this.sectionID = sectionID;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getObjectView() {
        return objectView;
    }

    public void setObjectView(String objectView) {
        this.objectView = objectView;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String pbjectType) {
        this.objectType = pbjectType;
    }

    public String getChartDisplay() {
        return chartDisplay;
    }

    public void setChartDisplay(String chartDisplay) {
        this.chartDisplay = chartDisplay;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getGroupCase() {
        return groupCase;
    }

    public void setGroupCase(String groupCase) {
        this.groupCase = groupCase;
    }

    public String getReportBlocks() {
        return reportBlocks;
    }

    public void setReportBlocks(String reportBlocks) {
        this.reportBlocks = reportBlocks;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String reportName;
        private String baseExternalID;
        private String organizationID;
        private String dateIn;
        private String dateOut;
        private String sessionID;
        private String stationID;
        private String sectionID;
        private String paymentType;
        private String personID;
        private String objectView;
        private String objectType;
        private String chartDisplay;
        private String sortDirection;
        private String groupCase;
        private String reportBlocks;

        public Builder reportName(String reportName) {
            this.reportName = reportName;
            return this;
        }

        public Builder baseExternalID(String baseExternalID) {
            this.baseExternalID = baseExternalID;
            return this;
        }

        public Builder organizationID(String organizationID) {
            this.organizationID = organizationID;
            return this;
        }

        public Builder dateIn(String dateIn) {
            this.dateIn = dateIn;
            return this;
        }

        public Builder dateOut(String dateOut) {
            this.dateOut = dateOut;
            return this;
        }

        public Builder sessionID(String sessionID) {
            this.sessionID = sessionID;
            return this;
        }

        public Builder stationID(String stationID) {
            this.stationID = stationID;
            return this;
        }

        public Builder sectionID(String sectionID) {
            this.sectionID = sectionID;
            return this;
        }

        public Builder paymentType(String paymentType) {
            this.paymentType = paymentType;
            return this;
        }

        public Builder personID(String personID) {
            this.personID = personID;
            return this;
        }

        public Builder objectView(String objectView) {
            this.objectView = objectView;
            return this;
        }

        public Builder objectType(String objectType) {
            this.objectType = objectType;
            return this;
        }

        public Builder chartDisplay(String chartDisplay) {
            this.chartDisplay = chartDisplay;
            return this;
        }

        public Builder sortDirection(String sortDirection) {
            this.sortDirection = sortDirection;
            return this;
        }

        public Builder groupCase(String groupCase) {
            this.groupCase = groupCase;
            return this;
        }

        public Builder reportBlocks(String reportBlocks) {
            this.reportBlocks = reportBlocks;
            return this;
        }

        public UploadResponse build() {
            return new UploadResponse(reportName,
                    TextUtils.isEmpty(baseExternalID)? "|" : baseExternalID,
                    TextUtils.isEmpty(organizationID)? "|" : organizationID,
                    dateIn, dateOut,
                    TextUtils.isEmpty(sessionID)? "|" : sessionID,
                    TextUtils.isEmpty(stationID)? "|" : stationID,
                    TextUtils.isEmpty(sectionID)? "|" : sectionID,
                    TextUtils.isEmpty(paymentType)? "|" : paymentType,
                    TextUtils.isEmpty(personID)? "|" : personID,
                    TextUtils.isEmpty(objectView)? "0" : objectView,
                    TextUtils.isEmpty(objectType)? "|" : objectType,
                    TextUtils.isEmpty(chartDisplay)? "|" : chartDisplay,
                    TextUtils.isEmpty(sortDirection)? "0" : sortDirection,
                    TextUtils.isEmpty(groupCase)? "|" : groupCase,
                    TextUtils.isEmpty(reportBlocks)? "0|1|2|3" : reportBlocks);
        }
    }

}

