clean:
	cd cors-present/webapp/ && ant clean && cd - && cd cors-not-present/webapp && ant clean && cd -
cors:
	cd cors-present/webapp/ && ant clean && ant && cd ../client-shell && sleep 15 && ./sample-invocation && cd ../..
no-cors:
	cd cors-not-present/webapp/ && ant clean && ant && cd ../client-shell && sleep 15 && ./sample-invocation && cd ../..
