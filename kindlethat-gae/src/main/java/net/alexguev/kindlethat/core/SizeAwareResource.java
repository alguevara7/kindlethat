package net.alexguev.kindlethat.core;

import org.springframework.core.io.Resource;

public interface SizeAwareResource extends Resource {

	long length();

}
