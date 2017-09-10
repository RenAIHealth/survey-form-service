package com.stardust.renai.survey.common;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.GenericConversionService;

public class Selection {

    public enum Operand {
        AND(" and "), OR(" or "), EMPTY("");

        private String op = "";

        Operand(String op) {
            this.op = op;
        }

        public String getOperand() {
            return this.op;
        }

    }

    public enum Operator {
        EQUAL("="),NOT_EQUAL("!="),GREATER(">"),GREATER_EQUAL(">="),LESS("<"),LESS_EQUAL("<="),LIKE(" like "),IS_NULL( "" ), NOT_NULL( "" ), EXSITS ("");

        private String opt = "";

        Operator(String opt) {
            this.opt = opt;
        }

        public String getOperator() {
            return this.opt;
        }

    }

    private Operand operand;
    private Operator operator;
    private String property;
    private Object value;
    private String paramName = "";

    @Autowired
    private GenericConversionService conversionService;

    public Selection() {

    }

    public Selection(final String p, final Operator opt) {
        this(p, opt, null, Operand.EMPTY);
    }

    public Selection(final String p, final Operator opt, final Object v) {
        this(p, opt, v, Operand.EMPTY);
    }

    public Selection(final String p, final Operator opt, final Object v, final String paramName) {
        this(p, opt, v, Operand.EMPTY);
        this.setParameterName(paramName);
    }

    public Selection(final String p, final Operator opt, final Object v, final Operand op) {
        property = p;
        operator = opt;
        value = v;
        operand = op;
        paramName = property;
    }

    public Selection(final String p, final Operator opt, final Object v, final Operand op, final String paramName) {
        property = p;
        operator = opt;
        value = v;
        operand = op;
        this.setParameterName(paramName);
    }

    public void setParameterName(String n) {
        paramName = n;
    }

    public String getParameterName(){
        return paramName;
    }

    public Operand getOperand(){
        return this.operand;
    }

    public Operator getOperator(){
        return this.operator;
    }

    public String getProperty(){
        return this.property;
    }

    public Object getValue(){
        return this.value;
    }

    @Override
    public String toString() {
        return operand + "(" + property + operator + value + ")";
    }
}


