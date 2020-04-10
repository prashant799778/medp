import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Endorse1Component } from './endorse1.component';

describe('Endorse1Component', () => {
  let component: Endorse1Component;
  let fixture: ComponentFixture<Endorse1Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Endorse1Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Endorse1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
