package com.jian.retrorx.bean;

public class StockBean {
    /**
     * symbol : 1233
     * revokeUrl : https://h5trade.china-invs.cn/h5/index.html#!/deal/login?logintype=1
     * buyUrl : https://h5trade.china-invs.cn/h5/index.html#!/deal/login?logintype=1
     * exterbrowser : false
     * type : 0
     * middleDes : 全牌照综合型券商
     * openUrl : https://kaihu.china-invs.cn/khh5/m/open/index.html?short_code=SHQLWLKJPbMAeAmypA
     * queryUrl : https://h5trade.china-invs.cn/h5/index.html#!/deal/login?logintype=1
     * smallDes : 万2.5佣金,全天开户
     * loginUrl : https://h5trade.china-invs.cn/h5/index.html#!/deal/login?logintype=1
     * sellUrl : https://h5trade.china-invs.cn/h5/index.html#!/deal/login?logintype=1
     * name : 中国中投证券
     * canOpen : true
     * logo : http://121.14.143.51:9090/sams/images/appimg/bk_zhongtou.png
     * defalut : true
     */

    private int symbol;
    private String revokeUrl;
    private String buyUrl;
    private boolean exterbrowser;
    private int type;
    private String middleDes;
    private String openUrl;
    private String queryUrl;
    private String smallDes;
    private String loginUrl;
    private String sellUrl;
    private String name;
    private boolean canOpen;
    private String logo;
    private boolean defalut;

    public int getSymbol() {
        return symbol;
    }

    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }

    public String getRevokeUrl() {
        return revokeUrl;
    }

    public void setRevokeUrl(String revokeUrl) {
        this.revokeUrl = revokeUrl;
    }

    public String getBuyUrl() {
        return buyUrl;
    }

    public void setBuyUrl(String buyUrl) {
        this.buyUrl = buyUrl;
    }

    public boolean isExterbrowser() {
        return exterbrowser;
    }

    public void setExterbrowser(boolean exterbrowser) {
        this.exterbrowser = exterbrowser;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMiddleDes() {
        return middleDes;
    }

    public void setMiddleDes(String middleDes) {
        this.middleDes = middleDes;
    }

    public String getOpenUrl() {
        return openUrl;
    }

    public void setOpenUrl(String openUrl) {
        this.openUrl = openUrl;
    }

    public String getQueryUrl() {
        return queryUrl;
    }

    public void setQueryUrl(String queryUrl) {
        this.queryUrl = queryUrl;
    }

    public String getSmallDes() {
        return smallDes;
    }

    public void setSmallDes(String smallDes) {
        this.smallDes = smallDes;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getSellUrl() {
        return sellUrl;
    }

    public void setSellUrl(String sellUrl) {
        this.sellUrl = sellUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCanOpen() {
        return canOpen;
    }

    public void setCanOpen(boolean canOpen) {
        this.canOpen = canOpen;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public boolean isDefalut() {
        return defalut;
    }

    public void setDefalut(boolean defalut) {
        this.defalut = defalut;
    }
}
