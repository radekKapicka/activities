package com.example.activities.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class WorkRegisters {

    private List<WorkRegister> registers;

    @XmlElement
    public List<WorkRegister> getRegisterList() {
        if (registers == null) {
            registers = new ArrayList<>();
        }
        return registers;
    }

}
