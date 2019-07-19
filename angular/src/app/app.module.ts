import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CoreModule } from './shared/code.module';
import { VisitorOverviewPageComponent } from './visitor-overview-page/visitor-overview-page.component';
import { VisitorCodeComponent } from './visitor-overview-page/components/visitor-code/visitor-code.component';
import { VisitorCurrentCodeComponent } from './visitor-overview-page/components/visitor-current-code/visitor-current-code.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { OwnerOverviewPageComponent } from './owner-overview-page/owner-overview-page.component';
import { OwnerCurrentCodeComponent } from './owner-overview-page/components/owner-current-code/owner-current-code.component';
import { OwnerNextCodeComponent } from './owner-overview-page/components/owner-next-code/owner-next-code.component';
import { StartQueueComponent } from './owner-overview-page/components/start-queue/start-queue.component';

import { AccessCodeService } from './shared/services/access-code.service';
import { QueueService } from './shared/services/queue.service';
import { LocalStorageService } from './visitor-overview-page/services/local-storage.service';
import { VisitorEstimatedTimeComponent } from './visitor-overview-page/components/visitor-estimated-time/visitor-estimated-time.component';

@NgModule({
  declarations: [
    AppComponent,
    VisitorOverviewPageComponent,
    VisitorCodeComponent,
    VisitorCurrentCodeComponent,
    LoginPageComponent,
    OwnerOverviewPageComponent,
    OwnerCurrentCodeComponent,
    OwnerNextCodeComponent,
    StartQueueComponent,
    VisitorEstimatedTimeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CoreModule
  ],
  providers: [
    AccessCodeService,
    QueueService,
    LocalStorageService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
