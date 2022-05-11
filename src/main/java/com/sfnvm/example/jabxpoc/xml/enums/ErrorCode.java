package com.sfnvm.example.jabxpoc.xml.enums;

public enum ErrorCode {
    SUCCESS("1", "Thành công"),
    // TODO: warning
    ERROR_SERVICE("-1", "Lỗi kết nối dịch vụ"),

    XML_SCHEMA_INVALID("1000", "Sai định dạng thông điệp"),
    MLTDIEP_INVALID("1001", "Sai thông tin Mã loại thông điệp"),
    DUPLICATE_TRANSCODE("1002", "Trùng thông điệp"),

    TVAN_NOT_SIGNED("2002", "Tổ chức TVAN chưa ký hợp đồng với TCT"),
    TVAN_EXPIRED("2003", "Hợp đồng cung cấp dịch vụ HĐĐT với TCT đã hết hiệu lực"),
    TVAN_PENDING("2004", "Tổ chức TVAN đang trong thời gian tạm ngừng cung cấp HĐĐT"),
    TVAN_TIN_INACTIVED("2005", "Trạng thái MST của tổ chức TVAN không hoạt động"),
    TVAN_NOT_AVAILABLE("2006", "Tổ chức TVAN chưa đến ngày cung cấp dịch vụ"),
    TVAN_TIN_NOTFOUND("2012", "MST của tổ chức TVAN không tồn tại"),

    ORG_NOT_SIGNED("2007", "Doanh nghiệp/Tổ chức chưa đăng ký kết nối tới Tổng cục Thuế thành công"),
    ORG_EXPIRED("2008", "Doanh nghiệp/Tổ chức đã hết hiệu lực kết nối trực tiếp với Tổng cục Thuế"),
    ORG_TIN_INACTIVED("2009", "Trạng thái MST của Doanh nghiệp/Tổ chức không hoạt động"),
    QUANTITY_INVALID("2010", "Số lượng hóa đơn không khớp giữa thông điệp và chi tiết"),

    CERT_INVALID("2011", "Chữ ký điện tử không hợp lệ");

    private final String key;

    private final String value;

    ErrorCode(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
