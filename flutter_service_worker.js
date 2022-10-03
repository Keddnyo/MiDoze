'use strict';
const MANIFEST = 'flutter-app-manifest';
const TEMP = 'flutter-temp-cache';
const CACHE_NAME = 'flutter-app-cache';
const RESOURCES = {
  "apple-touch-icon.png": "dce22e0fbfd2d8257a45d32a61d6fcc9",
"assets/AssetManifest.json": "e05dfa2940de4aeed5d6f320201a7a8e",
"assets/assets/images/amazfit_bip.png": "017ff034b3403cfffd77f5aa8ee716b3",
"assets/assets/images/apps/gadgetbridge.png": "e64aabf288fa7498fe4f9a83f514dde9",
"assets/assets/images/apps/huafetcher.png": "fc7bf72dfdb4790bd945a3623e336b1b",
"assets/assets/images/apps/master_for_amazfit.png": "e8d09f9335167db6fddb7a21f5701145",
"assets/assets/images/apps/master_for_mi_band.png": "7d116568999342da713f9e3baa28d308",
"assets/assets/images/apps/mi_bandage.png": "d7b832a3b50f82cbfc3e08d580c83678",
"assets/assets/images/apps/mi_fit.png": "3de5385f201f15452695ad789714b4a3",
"assets/assets/images/apps/mi_wear.png": "925ee3d1e24aea1a317d80a9c4015dfa",
"assets/assets/images/apps/notify_amazfit.png": "3874f5381b8a829aa6b3861e281fa08c",
"assets/assets/images/apps/notify_lite.png": "30b431f13616ad942b0691501d72e413",
"assets/assets/images/apps/notify_lite_mi_band.png": "906d59eb18d5365a18b7748e8058d069",
"assets/assets/images/apps/notify_mi_band.png": "10a46a96a4369ef6c7fedbc28689e535",
"assets/assets/images/apps/tools_and_amazfit.png": "44381e6f36ab92ea63d05981accf55d3",
"assets/assets/images/apps/tools_and_mi_band.png": "2116f22e9e3f2ebcf021712441f5b38b",
"assets/assets/images/apps/watch_droid_assistant.png": "6875206513b222696f67ac6914f48840",
"assets/assets/images/apps/zepp.png": "1d7525633fc5ef42a6a30490176a1a9f",
"assets/FontManifest.json": "dc3d03800ccca4601324923c0b1d6d57",
"assets/fonts/MaterialIcons-Regular.otf": "e7069dfd19b331be16bed984668fe080",
"assets/NOTICES": "b162209a90d0c9b4570bee1acf69a85b",
"assets/packages/cupertino_icons/assets/CupertinoIcons.ttf": "6d342eb68f170c97609e9da345464e5e",
"assets/shaders/ink_sparkle.frag": "a04e492a05f9fd1a8cc6f12163b184dd",
"favicon.ico": "cdffc3454ccd5ed64ea735332bf4c1f3",
"icons/Icon-192.png": "a9d7c143e5e102a40056cd857033c50e",
"icons/Icon-512.png": "0eafac00e85ef99e80acb5032487c105",
"icons/Icon-maskable-192.png": "6a77c3bfb4b42e7849763686381fc51e",
"icons/Icon-maskable-512.png": "364697a213df392204861dd088c3cd32",
"index.html": "7393bfe37adafd14cdb32a23d16064ca",
"/": "7393bfe37adafd14cdb32a23d16064ca",
"main.dart.js": "9b5b2d5fd2a62c71d6bf7d5eb7eeacf0",
"manifest.json": "bb61e72c4031ac52a46cd91e1ec34445",
"version.json": "eb25b2f87ab0f7f2cb7e2a7dda5fab84"
};

// The application shell files that are downloaded before a service worker can
// start.
const CORE = [
  "main.dart.js",
"index.html",
"assets/AssetManifest.json",
"assets/FontManifest.json"];
// During install, the TEMP cache is populated with the application shell files.
self.addEventListener("install", (event) => {
  self.skipWaiting();
  return event.waitUntil(
    caches.open(TEMP).then((cache) => {
      return cache.addAll(
        CORE.map((value) => new Request(value, {'cache': 'reload'})));
    })
  );
});

// During activate, the cache is populated with the temp files downloaded in
// install. If this service worker is upgrading from one with a saved
// MANIFEST, then use this to retain unchanged resource files.
self.addEventListener("activate", function(event) {
  return event.waitUntil(async function() {
    try {
      var contentCache = await caches.open(CACHE_NAME);
      var tempCache = await caches.open(TEMP);
      var manifestCache = await caches.open(MANIFEST);
      var manifest = await manifestCache.match('manifest');
      // When there is no prior manifest, clear the entire cache.
      if (!manifest) {
        await caches.delete(CACHE_NAME);
        contentCache = await caches.open(CACHE_NAME);
        for (var request of await tempCache.keys()) {
          var response = await tempCache.match(request);
          await contentCache.put(request, response);
        }
        await caches.delete(TEMP);
        // Save the manifest to make future upgrades efficient.
        await manifestCache.put('manifest', new Response(JSON.stringify(RESOURCES)));
        return;
      }
      var oldManifest = await manifest.json();
      var origin = self.location.origin;
      for (var request of await contentCache.keys()) {
        var key = request.url.substring(origin.length + 1);
        if (key == "") {
          key = "/";
        }
        // If a resource from the old manifest is not in the new cache, or if
        // the MD5 sum has changed, delete it. Otherwise the resource is left
        // in the cache and can be reused by the new service worker.
        if (!RESOURCES[key] || RESOURCES[key] != oldManifest[key]) {
          await contentCache.delete(request);
        }
      }
      // Populate the cache with the app shell TEMP files, potentially overwriting
      // cache files preserved above.
      for (var request of await tempCache.keys()) {
        var response = await tempCache.match(request);
        await contentCache.put(request, response);
      }
      await caches.delete(TEMP);
      // Save the manifest to make future upgrades efficient.
      await manifestCache.put('manifest', new Response(JSON.stringify(RESOURCES)));
      return;
    } catch (err) {
      // On an unhandled exception the state of the cache cannot be guaranteed.
      console.error('Failed to upgrade service worker: ' + err);
      await caches.delete(CACHE_NAME);
      await caches.delete(TEMP);
      await caches.delete(MANIFEST);
    }
  }());
});

// The fetch handler redirects requests for RESOURCE files to the service
// worker cache.
self.addEventListener("fetch", (event) => {
  if (event.request.method !== 'GET') {
    return;
  }
  var origin = self.location.origin;
  var key = event.request.url.substring(origin.length + 1);
  // Redirect URLs to the index.html
  if (key.indexOf('?v=') != -1) {
    key = key.split('?v=')[0];
  }
  if (event.request.url == origin || event.request.url.startsWith(origin + '/#') || key == '') {
    key = '/';
  }
  // If the URL is not the RESOURCE list then return to signal that the
  // browser should take over.
  if (!RESOURCES[key]) {
    return;
  }
  // If the URL is the index.html, perform an online-first request.
  if (key == '/') {
    return onlineFirst(event);
  }
  event.respondWith(caches.open(CACHE_NAME)
    .then((cache) =>  {
      return cache.match(event.request).then((response) => {
        // Either respond with the cached resource, or perform a fetch and
        // lazily populate the cache only if the resource was successfully fetched.
        return response || fetch(event.request).then((response) => {
          if (response && Boolean(response.ok)) {
            cache.put(event.request, response.clone());
          }
          return response;
        });
      })
    })
  );
});

self.addEventListener('message', (event) => {
  // SkipWaiting can be used to immediately activate a waiting service worker.
  // This will also require a page refresh triggered by the main worker.
  if (event.data === 'skipWaiting') {
    self.skipWaiting();
    return;
  }
  if (event.data === 'downloadOffline') {
    downloadOffline();
    return;
  }
});

// Download offline will check the RESOURCES for all files not in the cache
// and populate them.
async function downloadOffline() {
  var resources = [];
  var contentCache = await caches.open(CACHE_NAME);
  var currentContent = {};
  for (var request of await contentCache.keys()) {
    var key = request.url.substring(origin.length + 1);
    if (key == "") {
      key = "/";
    }
    currentContent[key] = true;
  }
  for (var resourceKey of Object.keys(RESOURCES)) {
    if (!currentContent[resourceKey]) {
      resources.push(resourceKey);
    }
  }
  return contentCache.addAll(resources);
}

// Attempt to download the resource online before falling back to
// the offline cache.
function onlineFirst(event) {
  return event.respondWith(
    fetch(event.request).then((response) => {
      return caches.open(CACHE_NAME).then((cache) => {
        cache.put(event.request, response.clone());
        return response;
      });
    }).catch((error) => {
      return caches.open(CACHE_NAME).then((cache) => {
        return cache.match(event.request).then((response) => {
          if (response != null) {
            return response;
          }
          throw error;
        });
      });
    })
  );
}
