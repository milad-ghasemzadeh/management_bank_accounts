package org.management.exception;

import org.springframework.http.HttpStatus;

public class ErrorMessageConstants {


    public interface InternalServer {
        String message = "ارور داخلی!!";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public interface NotNull {
        String message = "به خالی بودن یا درست وارد کردن این فیلد توجه کنید";
        HttpStatus status = HttpStatus.BAD_REQUEST;
    }

    public interface CouldNotFindPerson {
        String message = " هیچ شماره کارتی با این کد ملی یافت نشد";
        String developer_message = "we couldn't find any card with this nationalCode of person.";
        HttpStatus status = HttpStatus.NOT_FOUND;
    }

    public interface UniquesOfSenderCodeForPerson {
        String message = " هر فردی فقط باید از هر صادر کننده کارت فقط یک کارت با یک نوع داشته باشد";
        String developer_message = "every Person can have only one Card from any sender code and any cardType";
        HttpStatus status = HttpStatus.BAD_REQUEST;
    }

    public interface UniquesNameCard {
        String message = " شماره کارت باید مقداری منحصر بفرد باشد";
        String developer_message = "name card must be unique";
        HttpStatus status = HttpStatus.BAD_REQUEST;
    }

    public interface UniqueNationalCodePerson {
        String message = "این شماره ملی در سامانه ثبت شده است. لطفا از شماره ملی دیگری استفاده نمایید.";
        String developer_message = "nationalCode of person must be unique, Please Enter the other one.";
        HttpStatus status = HttpStatus.BAD_REQUEST;
    }

    public interface CouldNotFindPersonId {
        String message = "ایدی فرد یافت نشد";
        String developer_message = "couldn't find PersonId in database";
        HttpStatus status = HttpStatus.NOT_FOUND;
    }

    public interface CouldNotFindSenderId {
        String message = " کد صادر کننده کارت یافت نشد";
        String developer_message = "couldn't find senderId in database";
        HttpStatus status = HttpStatus.NOT_FOUND;
    }

    public interface FirstSixDigitCard {
        String message = "لطفا مشخصات کارت را مطابق با کد صادر کننده بانک درست وارد نمایید";
        String developer_message = "Please enter the valid nameCard according to SenderCode Bank(The First 6 Digits)";
        HttpStatus status = HttpStatus.BAD_REQUEST;
    }
}
