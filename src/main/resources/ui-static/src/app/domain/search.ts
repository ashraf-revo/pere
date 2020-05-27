import {Direction} from "./direction.enum";
import {PropertyDirection} from "./property-direction.enum";

export class Search {
  page: number;
  size: number;
  direction: Direction;
  property: PropertyDirection;
  value: string;
}

