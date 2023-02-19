import { Routes } from '@angular/router';


import { NgbdDropdownBasicComponent } from './dropdown-collapse/dropdown-collapse.component';
import { NgbdnavBasicComponent } from './nav/nav.component';
import { FullComponent } from '../layouts/full/full.component';


export const ComponentsRoutes: Routes = [
	{
		path: '',
		children: [
			{
				path: 'dropdown',
				component: NgbdDropdownBasicComponent
			},
			{
				path: 'nav',
				component: NgbdnavBasicComponent
			}
		]
	}
];
