package com.example.orderms.enums;



public enum OrderStatus {

    CREATED,              // order yeni yaradılıb (initial state)

    PRODUCT_RESERVING,    // product service çağırılıb

    PRODUCT_RESERVED,     // product uğurla rezerv olunub

    PAYMENT_PROCESSING,   // payment başlayıb

    COMPLETED,           // hər şey uğurla bitib

    FAILED,              // saga hər hansı step-də fail olub

    CANCELLED            // user/system tərəfindən ləğv olunub
}
