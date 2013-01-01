package com.jayway.service.fj;

import com.jayway.service.ReceiptService;
import com.jayway.service.ReceiptServiceTest;

public class FjReceiptServiceTest extends ReceiptServiceTest {

    @Override
    protected ReceiptService getReceiptService() {
        return new FjReceiptService();
    }
}
