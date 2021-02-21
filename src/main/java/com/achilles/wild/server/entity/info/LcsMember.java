package com.achilles.wild.server.entity.info;

import java.math.BigDecimal;
import java.util.Date;

public class LcsMember {
    private Long id;

    private String userUuid;

    private String mobile;

    private String sex;

    private String nickName;

    private String headerImg;

    private String password;

    private String isauth;

    private String realName;

    private String idcardNo;

    private String email;

    private String moneyPwd;

    private String inviteCode;

    private String industry;

    private String agescope;

    private String cityname;

    private String company;

    private Date createDate;

    private Date updateDate;

    private Integer delFlag;

    private Integer loginTimes;

    private String usercenterUuid;

    private String terminal;

    private Integer invitePartnerNum;

    private Long belongToPartner;

    private Date partnerDate;

    private Long belongToLcs;

    private Date lcsDate;

    private Integer isLcs;

    private String quastate;

    private String quaimgurl;

    private String imgtype;

    private Integer isNew;

    private Integer isAdmin;

    private Integer customerNum;

    private BigDecimal totalSaleAmount;

    private String isCrm;

    private Date newbieCloseTime;

    private Integer is9jiaMember;

    private String fuyouStatus;

    private Date registerCrmDate;

    private Integer isJ9Customer;

    private String remark;

    private Short isModifyMoneyPwd;

    private Short visitStatus;

    private BigDecimal assLevel;

    private String dataOrigin;

    private Long manageId;

    private Date manageDate;

    private Date opDimissionTime;

    private String bindSwitch;

    private String belongRegion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid == null ? null : userUuid.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg == null ? null : headerImg.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getIsauth() {
        return isauth;
    }

    public void setIsauth(String isauth) {
        this.isauth = isauth == null ? null : isauth.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getIdcardNo() {
        return idcardNo;
    }

    public void setIdcardNo(String idcardNo) {
        this.idcardNo = idcardNo == null ? null : idcardNo.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getMoneyPwd() {
        return moneyPwd;
    }

    public void setMoneyPwd(String moneyPwd) {
        this.moneyPwd = moneyPwd == null ? null : moneyPwd.trim();
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode == null ? null : inviteCode.trim();
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry == null ? null : industry.trim();
    }

    public String getAgescope() {
        return agescope;
    }

    public void setAgescope(String agescope) {
        this.agescope = agescope == null ? null : agescope.trim();
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname == null ? null : cityname.trim();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getLoginTimes() {
        return loginTimes;
    }

    public void setLoginTimes(Integer loginTimes) {
        this.loginTimes = loginTimes;
    }

    public String getUsercenterUuid() {
        return usercenterUuid;
    }

    public void setUsercenterUuid(String usercenterUuid) {
        this.usercenterUuid = usercenterUuid == null ? null : usercenterUuid.trim();
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal == null ? null : terminal.trim();
    }

    public Integer getInvitePartnerNum() {
        return invitePartnerNum;
    }

    public void setInvitePartnerNum(Integer invitePartnerNum) {
        this.invitePartnerNum = invitePartnerNum;
    }

    public Long getBelongToPartner() {
        return belongToPartner;
    }

    public void setBelongToPartner(Long belongToPartner) {
        this.belongToPartner = belongToPartner;
    }

    public Date getPartnerDate() {
        return partnerDate;
    }

    public void setPartnerDate(Date partnerDate) {
        this.partnerDate = partnerDate;
    }

    public Long getBelongToLcs() {
        return belongToLcs;
    }

    public void setBelongToLcs(Long belongToLcs) {
        this.belongToLcs = belongToLcs;
    }

    public Date getLcsDate() {
        return lcsDate;
    }

    public void setLcsDate(Date lcsDate) {
        this.lcsDate = lcsDate;
    }

    public Integer getIsLcs() {
        return isLcs;
    }

    public void setIsLcs(Integer isLcs) {
        this.isLcs = isLcs;
    }

    public String getQuastate() {
        return quastate;
    }

    public void setQuastate(String quastate) {
        this.quastate = quastate == null ? null : quastate.trim();
    }

    public String getQuaimgurl() {
        return quaimgurl;
    }

    public void setQuaimgurl(String quaimgurl) {
        this.quaimgurl = quaimgurl == null ? null : quaimgurl.trim();
    }

    public String getImgtype() {
        return imgtype;
    }

    public void setImgtype(String imgtype) {
        this.imgtype = imgtype == null ? null : imgtype.trim();
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Integer getCustomerNum() {
        return customerNum;
    }

    public void setCustomerNum(Integer customerNum) {
        this.customerNum = customerNum;
    }

    public BigDecimal getTotalSaleAmount() {
        return totalSaleAmount;
    }

    public void setTotalSaleAmount(BigDecimal totalSaleAmount) {
        this.totalSaleAmount = totalSaleAmount;
    }

    public String getIsCrm() {
        return isCrm;
    }

    public void setIsCrm(String isCrm) {
        this.isCrm = isCrm == null ? null : isCrm.trim();
    }

    public Date getNewbieCloseTime() {
        return newbieCloseTime;
    }

    public void setNewbieCloseTime(Date newbieCloseTime) {
        this.newbieCloseTime = newbieCloseTime;
    }

    public Integer getIs9jiaMember() {
        return is9jiaMember;
    }

    public void setIs9jiaMember(Integer is9jiaMember) {
        this.is9jiaMember = is9jiaMember;
    }

    public String getFuyouStatus() {
        return fuyouStatus;
    }

    public void setFuyouStatus(String fuyouStatus) {
        this.fuyouStatus = fuyouStatus == null ? null : fuyouStatus.trim();
    }

    public Date getRegisterCrmDate() {
        return registerCrmDate;
    }

    public void setRegisterCrmDate(Date registerCrmDate) {
        this.registerCrmDate = registerCrmDate;
    }

    public Integer getIsJ9Customer() {
        return isJ9Customer;
    }

    public void setIsJ9Customer(Integer isJ9Customer) {
        this.isJ9Customer = isJ9Customer;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Short getIsModifyMoneyPwd() {
        return isModifyMoneyPwd;
    }

    public void setIsModifyMoneyPwd(Short isModifyMoneyPwd) {
        this.isModifyMoneyPwd = isModifyMoneyPwd;
    }

    public Short getVisitStatus() {
        return visitStatus;
    }

    public void setVisitStatus(Short visitStatus) {
        this.visitStatus = visitStatus;
    }

    public BigDecimal getAssLevel() {
        return assLevel;
    }

    public void setAssLevel(BigDecimal assLevel) {
        this.assLevel = assLevel;
    }

    public String getDataOrigin() {
        return dataOrigin;
    }

    public void setDataOrigin(String dataOrigin) {
        this.dataOrigin = dataOrigin == null ? null : dataOrigin.trim();
    }

    public Long getManageId() {
        return manageId;
    }

    public void setManageId(Long manageId) {
        this.manageId = manageId;
    }

    public Date getManageDate() {
        return manageDate;
    }

    public void setManageDate(Date manageDate) {
        this.manageDate = manageDate;
    }

    public Date getOpDimissionTime() {
        return opDimissionTime;
    }

    public void setOpDimissionTime(Date opDimissionTime) {
        this.opDimissionTime = opDimissionTime;
    }

    public String getBindSwitch() {
        return bindSwitch;
    }

    public void setBindSwitch(String bindSwitch) {
        this.bindSwitch = bindSwitch == null ? null : bindSwitch.trim();
    }

    public String getBelongRegion() {
        return belongRegion;
    }

    public void setBelongRegion(String belongRegion) {
        this.belongRegion = belongRegion == null ? null : belongRegion.trim();
    }
}