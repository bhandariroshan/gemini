package com.example.rbhandari.datasyncapplication.datamodels;
import android.support.v4.app.INotificationSideChannel;

import com.orm.SugarRecord;
import java.util.Date;


public class Feature extends SugarRecord<Feature> {
    private String parseId;
    private Long formId;
    private Long auditId;
    private Long zoneId;
    private Long typeId;

    private  String belongsTo;
    private  String dataType;


    private  String key;
    private  String valueString;
    private  Integer valueInt;
    private  Double valueDouble;

    private  Date created;
    private  Date updated;

    public Feature(){

    }

    public Feature(String parseId, Long auditId, Long zoneId, Long typeId,Long formId,
                   String belongsTo, String dataType,
                   String key, String valueString, Integer valueInt, Double valueDouble){
        this.parseId = parseId;
        this.formId = formId;
        this.belongsTo = belongsTo;
        this.dataType = dataType;
        this.auditId = auditId;
        this.zoneId = zoneId;
        this.typeId = typeId;
        this.key = key;
        this.valueString = valueString;
        this.valueInt = valueInt;
        this.valueDouble = valueDouble;
    }

    public void setParseId(String parseId) {
        this.parseId = parseId;
    }

    public String getParseId() {
        return parseId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public Long getAuditId() {
        return auditId;
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Double getValueDouble() {
        return valueDouble;
    }

    public Long getFormId() {
        return formId;
    }

    public Integer getValueInt() {
        return valueInt;
    }

    public String getBelongsTo() {
        return belongsTo;
    }

    public String getDataType() {
        return dataType;
    }

    public String getKey() {
        return key;
    }

    public String getValueString() {
        return valueString;
    }

    public void setBelongsTo(String belongsTo) {
        this.belongsTo = belongsTo;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValueDouble(Double valueDouble) {
        this.valueDouble = valueDouble;
    }

    public void setValueInt(Integer valueInt) {
        this.valueInt = valueInt;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }
}