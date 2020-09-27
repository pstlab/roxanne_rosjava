package com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.compiler;

import com.github.roxanne_rosjava.roxanne_rosjava_core.ai.framework.compiler.ddl.v3.DDLv3Compiler;

/**
 * 
 * @author anacleto
 *
 */
public enum DomainCompilerType 
{
	/**
	 * 
	 */
	DDLv3(DDLv3Compiler.class.getName());
	
	private String cname;
	
	/**
	 * 
	 * @param cname
	 */
	private DomainCompilerType(String cname) {
		this.cname = cname;
	}

	/**
	 * 
	 * @return
	 */
	public String getCompilerClassName() {
		return this.cname;
	}
}
