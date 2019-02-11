package com.example.dell.clone;

import android.widget.TextView;

public interface AddToCart_Listener {
   void increment(AddToCartModalClass obj, TextView count);
    void decrement(AddToCartModalClass obj , TextView count);
}
