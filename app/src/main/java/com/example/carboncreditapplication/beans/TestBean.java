package com.example.carboncreditapplication.beans;

import java.util.List;

public class TestBean {

    /**
     * result : [{"commodity_id":1,"commodity_introduce":"green home","commodity_name":"守护地球","commodity_picture":"https://i.loli.net/2020/01/16/q65HkVWYveuXQzP.jpg","commodity_price":1,"commodity_price_original":1000000,"discount":[{"sill":100,"type":1,"value":100}],"discount_useful":1}]
     * status_code : 0000
     * status_msg : 处理成功
     */

    private String status_code;
    private String status_msg;
    private List<ResultBean> result;

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getStatus_msg() {
        return status_msg;
    }

    public void setStatus_msg(String status_msg) {
        this.status_msg = status_msg;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * commodity_id : 1
         * commodity_introduce : green home
         * commodity_name : 守护地球
         * commodity_picture : https://i.loli.net/2020/01/16/q65HkVWYveuXQzP.jpg
         * commodity_price : 1.0
         * commodity_price_original : 1000000.0
         * discount : [{"sill":100,"type":1,"value":100}]
         * discount_useful : 1
         */

        private int commodity_id;
        private String commodity_introduce;
        private String commodity_name;
        private String commodity_picture;
        private double commodity_price;
        private double commodity_price_original;
        private int discount_useful;
        private List<DiscountBean> discount;

        public int getCommodity_id() {
            return commodity_id;
        }

        public void setCommodity_id(int commodity_id) {
            this.commodity_id = commodity_id;
        }

        public String getCommodity_introduce() {
            return commodity_introduce;
        }

        public void setCommodity_introduce(String commodity_introduce) {
            this.commodity_introduce = commodity_introduce;
        }

        public String getCommodity_name() {
            return commodity_name;
        }

        public void setCommodity_name(String commodity_name) {
            this.commodity_name = commodity_name;
        }

        public String getCommodity_picture() {
            return commodity_picture;
        }

        public void setCommodity_picture(String commodity_picture) {
            this.commodity_picture = commodity_picture;
        }

        public double getCommodity_price() {
            return commodity_price;
        }

        public void setCommodity_price(double commodity_price) {
            this.commodity_price = commodity_price;
        }

        public double getCommodity_price_original() {
            return commodity_price_original;
        }

        public void setCommodity_price_original(double commodity_price_original) {
            this.commodity_price_original = commodity_price_original;
        }

        public int getDiscount_useful() {
            return discount_useful;
        }

        public void setDiscount_useful(int discount_useful) {
            this.discount_useful = discount_useful;
        }

        public List<DiscountBean> getDiscount() {
            return discount;
        }

        public void setDiscount(List<DiscountBean> discount) {
            this.discount = discount;
        }

        public static class DiscountBean {
            /**
             * sill : 100
             * type : 1
             * value : 100
             */

            private int sill;
            private int type;
            private int value;

            public int getSill() {
                return sill;
            }

            public void setSill(int sill) {
                this.sill = sill;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }
        }
    }
}
