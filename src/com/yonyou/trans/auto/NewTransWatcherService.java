package com.yonyou.trans.auto;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import com.yonyou.trans.auto.main.Main;

public class NewTransWatcherService {

	private final WatchService watcher;

	public NewTransWatcherService(Path path) throws IOException {
		watcher = FileSystems.getDefault().newWatchService();
		path.register(watcher, ENTRY_CREATE);
	}

	public void handleEvents() throws InterruptedException {
		while (true) {
			WatchKey key = watcher.take();
			for (WatchEvent<?> event : key.pollEvents()) {
				@SuppressWarnings("rawtypes")
				WatchEvent.Kind kind = event.kind();
				if (kind == OVERFLOW) {// 事件可能lost or discarded
					continue;
				}
				Main.main(null);
			}
			if (!key.reset()) {
				break;
			}
		}
	}
}
