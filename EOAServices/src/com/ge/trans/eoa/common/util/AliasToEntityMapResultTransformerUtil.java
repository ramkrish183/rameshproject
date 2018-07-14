package com.ge.trans.eoa.common.util;

import org.hibernate.transform.AliasToEntityMapResultTransformer;

/**
 * Adding a wrapper class to avoid multiple instantiation of AliasToEntityMapResultTransformer
 * Note - instantiation of AliasToEntityMapResultTransformer is deprecated in higher version of hibernate(>=3.2.7).
 * This utility is used only if hibernate version used is less than 3.2.6 
 */
public class AliasToEntityMapResultTransformerUtil {
	private static AliasToEntityMapResultTransformer alias_instance = null;
	private AliasToEntityMapResultTransformerUtil(){}
	public static AliasToEntityMapResultTransformer getInstance(){
		if(alias_instance == null)
			alias_instance = new AliasToEntityMapResultTransformer();
		return alias_instance;
	}
}
