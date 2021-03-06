/**
 * Copyright 2014 NAVER Corp.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.navercorp.pinpoint.plugin.axis2.jaxws;

import static com.navercorp.pinpoint.common.trace.ServiceTypeProperty.*;

import com.navercorp.pinpoint.common.trace.ServiceType;
import com.navercorp.pinpoint.common.trace.ServiceTypeFactory;

public final class Axis2JaxWsConstants {
    private Axis2JaxWsConstants() {
    }

    public static final String TYPE_NAME = "AXIS2_JAXWS";

    public static final ServiceType AXIS2_JAXWS_METHOD = ServiceTypeFactory.of(1071, "AXIS2_JAXWS_METHOD");

    public static final String METADATA_TRACE = "trace";
    public static final String METADATA_ASYNC = "async";
    public static final String METADATA_ASYNC_TRACE_ID = "asyncTraceId";

    public static final String ATTRIBUTE_PINPOINT_TRACE = "PINPOINT_TRACE";
}
