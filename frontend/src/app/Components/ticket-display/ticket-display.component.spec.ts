import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketDisplayComponent } from './ticket-display.component';

describe('TicketDisplayComponent', () => {
  let component: TicketDisplayComponent;
  let fixture: ComponentFixture<TicketDisplayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TicketDisplayComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TicketDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
