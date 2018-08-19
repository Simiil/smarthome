/**
 * Copyright (c) 2014,2018 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.smarthome.binding.hue.handler.sensors;

import static org.eclipse.smarthome.binding.hue.HueBindingConstants.THING_TYPE_PRESENCE_SENSOR;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.binding.hue.HueBindingConstants;
import org.eclipse.smarthome.binding.hue.handler.HueSensorHandler;
import org.eclipse.smarthome.binding.hue.internal.FullSensor;
import org.eclipse.smarthome.binding.hue.internal.HueBridge;
import org.eclipse.smarthome.config.core.Configuration;
import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;

/**
 * Presence Sensor
 *
 * @author Samuel Leisering - Added sensor support
 *
 */
public class PresenceHandler extends HueSensorHandler {
    public static final Set<ThingTypeUID> SUPPORTED_THING_TYPES = Stream.of(THING_TYPE_PRESENCE_SENSOR)
            .collect(Collectors.toSet());

    public PresenceHandler(Thing thing) {
        super(thing);
    }

    @Override
    protected void doSensorStateChanged(@Nullable HueBridge bridge, @NonNull FullSensor sensor, Configuration config) {
        Object btn = sensor.getState().get("presence");
        boolean val = Boolean.parseBoolean(String.valueOf(btn));
        OnOffType dt = val ? OnOffType.ON : OnOffType.OFF;
        updateState(HueBindingConstants.CHANNEL_PRESENCE, dt);

        if (sensor.getConfig().containsKey(FullSensor.CONFIG_PRESENCE_SENSITIVITY)) {
            config.put(FullSensor.CONFIG_PRESENCE_SENSITIVITY,
                    sensor.getConfig().get(FullSensor.CONFIG_PRESENCE_SENSITIVITY));
        }
        if (sensor.getConfig().containsKey(FullSensor.CONFIG_PRESENCE_SENSITIVITY_MAX)) {
            config.put(FullSensor.CONFIG_PRESENCE_SENSITIVITY_MAX,
                    sensor.getConfig().get(FullSensor.CONFIG_PRESENCE_SENSITIVITY_MAX));
        }

    }

    @Override
    protected String getVendor(String modelId) {
        return "Philips";
    }
}