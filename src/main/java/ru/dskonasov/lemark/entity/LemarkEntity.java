/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dskonasov.lemark.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dmitriy Konasov
 */
@Entity
@Table(name="lemark_entities")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@XmlTransient
public class LemarkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    protected long id;
    
    public LemarkEntity(){}
    
    public long getId(){
        return this.id;
    }
}
