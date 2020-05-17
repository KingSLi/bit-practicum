package service;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Money")
public class Money {

    @Id
    private Double temperature;
    private Double money;

    public Money(Double temperature, Double predict) {
        this.temperature = temperature;
        this.money =  predict;
    }

    public Money() {}

    public Double getMoney() {
        return money;
    }
}