import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { AppModule } from './app/core/app.module';


// Entry point of angular, initiates the root appModule
platformBrowserDynamic().bootstrapModule(AppModule, {
  ngZoneEventCoalescing: true,
})
  .catch(err => console.error(err));
