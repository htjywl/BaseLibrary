package com.example.administrator.baseproject.bean;

import java.util.List;

/**
 * author      : dwm
 * e-mail      : dwm@gzhtedu.cn
 * update time : 2020/8/17 14:27
 * version     : 1.0
 * desc        :
 */
public class TestBean {

    /**
     * app_enc_key : 6089120412HTJYEF
     * is_white : 0
     * tips : 密码长度6-20位,字母、数字和符号至少包含两种
     * cj_title : 高考成绩
     * alert_index : {}
     * alert_app_index : {}
     * app_version_info : {"code":"92","ver":"3.8.8","content":"优化山东考区志愿填报界面\n2、报考大学累计帮助400多万考生，升学卡与夺魁卡助你考上理想院校，快来开通吧","forcible":"0","text":"","update_time":1597648700,"addr":"https://cjcdn1.gzhtedu.cn/app/baokaodaxue-3.8.8.apk"}
     * home_icons : [{"name":"院校大全","img":"/images/bkdx_icon/20200814/159738414237923.png","type":"1"},{"name":"专业大全","img":"/images/bkdx_icon/20200814/159737494215194.png","type":"2"},{"name":"高考提分","img":"/images/bkdx_icon/20200814/159737495421403.png","type":"8"},{"name":"职业百科","img":"/images/bkdx_icon/20200814/159737496588020.png","type":"3"},{"name":"性格测试","img":"/images/bkdx_icon/20200814/159737497882719.png","type":"4"},{"name":"招生计划","img":"/images/bkdx_icon/20200814/159737498933520.png","type":"10"},{"name":"查专业线","img":"/images/bkdx_icon/20200814/159737500435965.png","type":"13"},{"name":"在线课堂","img":"/images/bkdx_icon/20200814/159737501789957.png","type":"7"},{"name":"省控线","img":"/images/bkdx_icon/20200814/159737502829650.png","type":"5"},{"name":"新高考","img":"/images/bkdx_icon/20200814/159737504035122.png","type":"6"}]
     * imgurl : https://imgcdn1.gzhtedu.cn
     * vip_zone : [{"img":"https://imgcdn1.gzhtedu.cn/images/bkdx_vip_icons/20200623/159288420530036.png","mod_type":"15"},{"img":"https://imgcdn1.gzhtedu.cn/images/bkdx_vip_icons/20200623/159288422424775.png","mod_type":"16"},{"img":"https://imgcdn1.gzhtedu.cn/images/bkdx_vip_icons/20200623/159288423956845.png","mod_type":"17"},{"img":"https://imgcdn1.gzhtedu.cn/images/bkdx_vip_icons/20200623/159288425839656.png","mod_type":"18"}]
     * kt_vip_banner : https://imgcdn1.gzhtedu.cn/images/bkdx_vip_success_case/20200710/159436015069804.jpeg
     * vip_expert : https://imgcdn1.gzhtedu.cn/images/bkdx_vip_success_case/20200708/159420373737849.png
     */

    private String app_enc_key;
    private String is_white;
    private String tips;
    private String cj_title;
    private AlertIndexBean alert_index;
    private AlertAppIndexBean alert_app_index;
    private AppVersionInfoBean app_version_info;
    private String imgurl;
    private String kt_vip_banner;
    private String vip_expert;
    private List<HomeIconsBean> home_icons;
    private List<VipZoneBean> vip_zone;

    public String getApp_enc_key() {
        return app_enc_key;
    }

    public void setApp_enc_key(String app_enc_key) {
        this.app_enc_key = app_enc_key;
    }

    public String getIs_white() {
        return is_white;
    }

    public void setIs_white(String is_white) {
        this.is_white = is_white;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getCj_title() {
        return cj_title;
    }

    public void setCj_title(String cj_title) {
        this.cj_title = cj_title;
    }

    public AlertIndexBean getAlert_index() {
        return alert_index;
    }

    public void setAlert_index(AlertIndexBean alert_index) {
        this.alert_index = alert_index;
    }

    public AlertAppIndexBean getAlert_app_index() {
        return alert_app_index;
    }

    public void setAlert_app_index(AlertAppIndexBean alert_app_index) {
        this.alert_app_index = alert_app_index;
    }

    public AppVersionInfoBean getApp_version_info() {
        return app_version_info;
    }

    public void setApp_version_info(AppVersionInfoBean app_version_info) {
        this.app_version_info = app_version_info;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getKt_vip_banner() {
        return kt_vip_banner;
    }

    public void setKt_vip_banner(String kt_vip_banner) {
        this.kt_vip_banner = kt_vip_banner;
    }

    public String getVip_expert() {
        return vip_expert;
    }

    public void setVip_expert(String vip_expert) {
        this.vip_expert = vip_expert;
    }

    public List<HomeIconsBean> getHome_icons() {
        return home_icons;
    }

    public void setHome_icons(List<HomeIconsBean> home_icons) {
        this.home_icons = home_icons;
    }

    public List<VipZoneBean> getVip_zone() {
        return vip_zone;
    }

    public void setVip_zone(List<VipZoneBean> vip_zone) {
        this.vip_zone = vip_zone;
    }

    public static class AlertIndexBean {
    }

    public static class AlertAppIndexBean {
    }

    public static class AppVersionInfoBean {
        /**
         * code : 92
         * ver : 3.8.8
         * content : 优化山东考区志愿填报界面
         2、报考大学累计帮助400多万考生，升学卡与夺魁卡助你考上理想院校，快来开通吧
         * forcible : 0
         * text : 
         * update_time : 1597648700
         * addr : https://cjcdn1.gzhtedu.cn/app/baokaodaxue-3.8.8.apk
         */

        private String code;
        private String ver;
        private String content;
        private String forcible;
        private String text;
        private int update_time;
        private String addr;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getVer() {
            return ver;
        }

        public void setVer(String ver) {
            this.ver = ver;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getForcible() {
            return forcible;
        }

        public void setForcible(String forcible) {
            this.forcible = forcible;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(int update_time) {
            this.update_time = update_time;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }
    }

    public static class HomeIconsBean {
        /**
         * name : 院校大全
         * img : /images/bkdx_icon/20200814/159738414237923.png
         * type : 1
         */

        private String name;
        private String img;
        private String type;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class VipZoneBean {
        /**
         * img : https://imgcdn1.gzhtedu.cn/images/bkdx_vip_icons/20200623/159288420530036.png
         * mod_type : 15
         */

        private String img;
        private String mod_type;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getMod_type() {
            return mod_type;
        }

        public void setMod_type(String mod_type) {
            this.mod_type = mod_type;
        }
    }

    @Override
    public String toString() {
        return "PhpBean{" +
                "app_enc_key='" + app_enc_key + '\'' +
                ", is_white='" + is_white + '\'' +
                ", tips='" + tips + '\'' +
                ", cj_title='" + cj_title + '\'' +
                ", alert_index=" + alert_index +
                ", alert_app_index=" + alert_app_index +
                ", app_version_info=" + app_version_info +
                ", imgurl='" + imgurl + '\'' +
                ", kt_vip_banner='" + kt_vip_banner + '\'' +
                ", vip_expert='" + vip_expert + '\'' +
                ", home_icons=" + home_icons +
                ", vip_zone=" + vip_zone +
                '}';
    }
}
