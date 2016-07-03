package org.pillar.codec.binary.schema;

import org.pillar.codec.binary.codec.Codec;

/**
 * Created by pillar on 2015/8/14.
 * 维护Field和Codec的对应关系
 */
public enum FType {
	UInt8 {
		@Override
		public Codec codec() {
			return null;
		}
	},
	UInt16 {
		@Override
		public Codec codec() {
			return null;
		}
	},
	UInt32 {
		@Override
		public Codec codec() {
			return null;
		}
	};

	public abstract Codec codec();


}
