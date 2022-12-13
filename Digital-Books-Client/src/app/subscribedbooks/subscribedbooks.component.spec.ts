import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubscribedbooksComponent } from './subscribedbooks.component';

describe('SubscribedbooksComponent', () => {
  let component: SubscribedbooksComponent;
  let fixture: ComponentFixture<SubscribedbooksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubscribedbooksComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubscribedbooksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
