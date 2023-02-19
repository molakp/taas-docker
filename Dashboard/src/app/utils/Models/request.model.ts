import { User } from "./user.model";

export interface Request {
	name?: string;
	surname?: string;
	username: string;
	password: string;
	roles?: string;
	user?: User;
}
