package com.pillar.skynet.facekiosk.capture;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.io.File;

@Value
@Builder
@NonFinal
public class CaptureConfiguration {

    private File captureRoot;

}
