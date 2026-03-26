package com.vn.service;

import com.vn.model.Playable;

public record AudioUpdateResult(Playable audio, boolean added) {
}
