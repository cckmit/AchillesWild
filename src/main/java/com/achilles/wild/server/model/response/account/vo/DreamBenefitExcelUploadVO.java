package com.achilles.wild.server.model.response.account.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;

import java.io.Serializable;
import java.util.Date;

/**
 * �ϴ���excel���ݽ���ʱ��Ӧ��ʵ��
 * �ֶ���indexΪexcel�е�ֵ����һ��Ϊ0;
 */
public class DreamBenefitExcelUploadVO implements Serializable {

    private static final long serialVersionUID = -4486741818794596662L;

    /**
     *   ��Ʒid
     */
    @ExcelProperty(index = 0)
    private Long itemId;

    @ExcelProperty("����")
    private String name;

    /**
     *   Ͷ��ʱ��
     */
    @ExcelProperty(index = 1)
    private Date publishDate;

    /**
     * Ͷ�Ž���ʱ��
     */
    @ExcelProperty(index = 2)
    private Date publishDateEnd;

    /**
     *   �μ�Ȩ����Ʒ����
     */
    @ExcelProperty(index = 3)
    private Integer itemCount;

    /**
     *   ���ɲ�������
     */
    @ExcelProperty(index = 4)
    @ColumnWidth(18)
    private Integer maxUserCount;

    @ExcelIgnore
    private Integer fire;


    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getPublishDateEnd() {
        return publishDateEnd;
    }

    public void setPublishDateEnd(Date publishDateEnd) {
        this.publishDateEnd = publishDateEnd;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public Integer getMaxUserCount() {
        return maxUserCount;
    }

    public void setMaxUserCount(Integer maxUserCount) {
        this.maxUserCount = maxUserCount;
    }

    public Integer getFire() {
        return fire;
    }

    public void setFire(Integer fire) {
        this.fire = fire;
    }
}
