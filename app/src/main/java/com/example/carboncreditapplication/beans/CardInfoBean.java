package com.example.carboncreditapplication.beans;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

public class CardInfoBean {

    @SerializedName("result")
    private CardInfoResultBean cardInfoResultBean;

    public CardInfoBean(){
        this.cardInfoResultBean = new CardInfoResultBean();
        this.cardInfoResultBean.setCardList(new CardInfoResultBean.CardList());
    }

    public CardInfoResultBean getCardInfoResultBean() {
        return cardInfoResultBean;
    }

    public void setCardInfoResultBean(CardInfoResultBean cardInfoResultBean) {
        this.cardInfoResultBean = cardInfoResultBean;
    }

    public static class CardInfoResultBean{
        public int getPageTotal() {
            return pageTotal;
        }

        public void setPageTotal(int pageTotal) {
            this.pageTotal = pageTotal;
        }

        public CardList getCardList() {
            return cardList;
        }

        public void setCardList(CardList cardList) {
            this.cardList = cardList;
        }

        @SerializedName("page_total")
        private int pageTotal;  //总页码数

        @SerializedName("card_list")
        private CardList cardList;

        public static class CardList{
            public int getCardId() {
                return cardId;
            }

            public void setCardId(int cardId) {
                this.cardId = cardId;
            }

            public int getUseStoreId() {
                return useStoreId;
            }

            public void setUseStoreId(int useStoreId) {
                this.useStoreId = useStoreId;
            }

            public String getCardName() {
                return cardName;
            }

            public void setCardName(String cardName) {
                this.cardName = cardName;
            }

            public int getCardType() {
                return cardType;
            }

            public void setCardType(int cardType) {
                this.cardType = cardType;
            }

            public int getUseType() {
                return useType;
            }

            public void setUseType(int useType) {
                this.useType = useType;
            }

            public int getSill() {
                return sill;
            }

            public void setSill(int sill) {
                this.sill = sill;
            }

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }

            public DateTime getDateTime() {
                return dateTime;
            }

            public void setDateTime(DateTime dateTime) {
                this.dateTime = dateTime;
            }

            @SerializedName("card_id")
            private int cardId;  //优惠券id
            @SerializedName("use_store_id")
            private int useStoreId;  //优惠券对应的商店
            @SerializedName("card_name")
            private String cardName;  //优惠券名称
            @SerializedName("card_type")
            private int cardType;  //优惠券类型
            @SerializedName("use_type")
            private int useType;  //优惠券使用类型
            @SerializedName("sill")
            private int sill;  //使用门槛
            @SerializedName("value")
            private int value;  //使用价值
            @SerializedName("expiration_time")
            private DateTime dateTime; //过期时间

        }



    }
}
