package com.deltaqin.seckill.dataobject;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class SequenceInfo implements Serializable {
    private String seqName;

    private Integer currentValue;

    private Integer step;

    private static final long serialVersionUID = 1L;

    public String getSeqName() {
        return seqName;
    }

    public void setSeqName(String seqName) {
        this.seqName = seqName;
    }

    public Integer getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Integer currentValue) {
        this.currentValue = currentValue;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", seqName=").append(seqName);
        sb.append(", currentValue=").append(currentValue);
        sb.append(", step=").append(step);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}