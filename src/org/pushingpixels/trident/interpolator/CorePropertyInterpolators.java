/*
 * Copyright (c) 2005-2010 Trident Kirill Grouchnikov. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *  o Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer. 
 *     
 *  o Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution. 
 *     
 *  o Neither the name of Trident Kirill Grouchnikov nor the names of 
 *    its contributors may be used to endorse or promote products derived 
 *    from this software without specific prior written permission. 
 *     
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
package org.pushingpixels.trident.interpolator;

import java.util.*;

/**
 *
 * @author Ernesto
 */
public class CorePropertyInterpolators implements PropertyInterpolatorSource {
	private Set<PropertyInterpolator> interpolators;

    /**
     *
     */
    public CorePropertyInterpolators() {
		this.interpolators = new HashSet<PropertyInterpolator>();
		this.interpolators.add(new IntegerPropertyInterpolator());
		this.interpolators.add(new FloatPropertyInterpolator());
		this.interpolators.add(new DoublePropertyInterpolator());
		this.interpolators.add(new LongPropertyInterpolator());
	}

    /**
     *
     * @return
     */
    @Override
	public Set<PropertyInterpolator> getPropertyInterpolators() {
		return Collections.unmodifiableSet(this.interpolators);
	}

	private static class FloatPropertyInterpolator implements
			PropertyInterpolator<Float> {
		@Override
		public Class getBasePropertyClass() {
			return Float.class;
		}

		@Override
		public Float interpolate(Float from, Float to, float timelinePosition) {
			return from + (to - from) * timelinePosition;
		}
	}

	private static class DoublePropertyInterpolator implements
			PropertyInterpolator<Double> {
		@Override
		public Class getBasePropertyClass() {
			return Double.class;
		}

		@Override
		public Double interpolate(Double from, Double to, float timelinePosition) {
			return from + (to - from) * timelinePosition;
		}
	}

	private static class IntegerPropertyInterpolator implements
			PropertyInterpolator<Integer> {
		@Override
		public Class getBasePropertyClass() {
			return Integer.class;
		}

		@Override
		public Integer interpolate(Integer from, Integer to,
				float timelinePosition) {
			return (int) (from + (to - from) * timelinePosition);
		}
	}

	private static class LongPropertyInterpolator implements
			PropertyInterpolator<Long> {
		@Override
		public Class getBasePropertyClass() {
			return Long.class;
		}

		@Override
		public Long interpolate(Long from, Long to, float timelinePosition) {
			return (long) (from + (to - from) * timelinePosition);
		}
	}
}
